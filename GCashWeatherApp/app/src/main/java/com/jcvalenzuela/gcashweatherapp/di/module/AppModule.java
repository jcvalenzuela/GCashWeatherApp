package com.jcvalenzuela.gcashweatherapp.di.module;

import static com.jcvalenzuela.gcashweatherapp.data.remote.client.ApiClient.BASE_URL_STRING;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.hasNetworkConnection;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.data.MainDataRepository;
import com.jcvalenzuela.gcashweatherapp.data.MainDataRepositoryImpl;
import com.jcvalenzuela.gcashweatherapp.data.local.cache.PrefSharedCacheData;
import com.jcvalenzuela.gcashweatherapp.data.local.cache.PrefSharedHelper;
import com.jcvalenzuela.gcashweatherapp.data.local.db.dao.AppDatabase;
import com.jcvalenzuela.gcashweatherapp.data.remote.ApiClientService;
import com.jcvalenzuela.gcashweatherapp.data.repository.local.LocalDataRepository;
import com.jcvalenzuela.gcashweatherapp.data.repository.local.LocalDataRepositoryImpl;
import com.jcvalenzuela.gcashweatherapp.data.repository.remote.RemoteWeatherDataRepository;
import com.jcvalenzuela.gcashweatherapp.data.repository.remote.RemoteWeatherDataRepositoryImpl;
import com.jcvalenzuela.gcashweatherapp.di.DatabaseInfo;
import com.jcvalenzuela.gcashweatherapp.helper.ConstantDeclarations;
import com.jcvalenzuela.gcashweatherapp.presentation.adapter.CurrentWeatherAdapter;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    public static Context provideApplicationContext(Application application){
        return application;
    }

    @Provides
    @Singleton
    public static ApiClientService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiClientService.class);
    }

    @Provides
    @Singleton
    public static Retrofit providerRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_STRING)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public static Cache provideCache(Context context) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    public static Interceptor provideInterceptor(Context context) {
        return chain -> {
            Request request = chain.request();
            if (!hasNetworkConnection(context)) {
                int maxStale = 60 * 60 * 24 * 28;
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale" + maxStale)
                        .build();
            } else {
                int maxAge = 5;
                request = request.newBuilder()
                        .header("Cache-Control", "public, max-age = " + maxAge)
                        .build();
            }
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(Interceptor interceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    public static AppDatabase providerAppDatabase(Context context, @DatabaseInfo String databaseName) {
        return Room.databaseBuilder(context, AppDatabase.class, databaseName)
                .allowMainThreadQueries().build();
    }

    @Provides
    @DatabaseInfo
    public static String provideDatabaseName() {
        return ConstantDeclarations.DATABASE_NAME;
    }

    @Provides
    @Singleton
    public static RemoteWeatherDataRepository provideRemoteWeatherDataRepository(RemoteWeatherDataRepositoryImpl remoteDataRepository) {
        return remoteDataRepository;
    }

    @Provides
    @Singleton
    public static LocalDataRepository provideLocalDataRepository(LocalDataRepositoryImpl localDataRepository) {
        return localDataRepository;
    }

    @Provides
    @Singleton
    public static MainDataRepository provideMainRepository(MainDataRepositoryImpl mainRepository) {
        return mainRepository;
    }

    @Provides
    @Singleton
    CurrentWeatherAdapter provideAdapter() {
        return new CurrentWeatherAdapter(new ArrayList<>());
    }

    @Provides
    @Singleton
    public static PrefSharedHelper provideSharedData(PrefSharedCacheData prefSharedCacheData) {
        return prefSharedCacheData;
    }

    @Provides
    @Singleton
    public static SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

}
