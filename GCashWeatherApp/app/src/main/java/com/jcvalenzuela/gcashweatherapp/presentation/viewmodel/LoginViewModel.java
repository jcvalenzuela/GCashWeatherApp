package com.jcvalenzuela.gcashweatherapp.presentation.viewmodel;

import static com.jcvalenzuela.gcashweatherapp.domain.utils.Utility.isStringEmpty;
import static com.jcvalenzuela.gcashweatherapp.domain.utils.Utility.isValidMail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jcvalenzuela.gcashweatherapp.data.MainDataRepositoryImpl;
import com.jcvalenzuela.gcashweatherapp.data.model.entity.LoginEntity;
import com.jcvalenzuela.gcashweatherapp.domain.LiveLoginData;
import com.jcvalenzuela.gcashweatherapp.domain.LoginDataModel;
import com.jcvalenzuela.gcashweatherapp.domain.LoginEnumState;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class LoginViewModel extends BaseViewModel implements LoginHelper {

    private static final String TAG = LoginViewModel.class.getSimpleName();
    //RxDisposable
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<String> emailRegistration = new MutableLiveData<>();
    public MutableLiveData<String> userNameRegistration = new MutableLiveData<>();
    public MutableLiveData<String> passwordRegistration = new MutableLiveData<>();
    public MutableLiveData<String> confirmPasswordRegistration = new MutableLiveData<>();
    public MutableLiveData<String> usernameLogin = new MutableLiveData<>();
    public MutableLiveData<String> passwordLogin = new MutableLiveData<>();

    private LoginDataModel loginDataModel;

    private MutableLiveData<LiveLoginData> liveLoginDataMutableLiveData;

    public LiveData<LiveLoginData> getLiveLoginDataLiveData() {
        return liveLoginDataMutableLiveData;
    }

    @Inject
    public LoginViewModel(MainDataRepositoryImpl mainDataRepository) {
        super(mainDataRepository);
        liveLoginDataMutableLiveData = new MutableLiveData<>();
        Log.e(TAG, "Init LoginViewModel");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    @Override
    public void onUserLogin() {
        loginDataModel = new LoginDataModel(
                usernameLogin.getValue(),
                passwordLogin.getValue(),
                "",
                ""
        );

        if (isStringEmpty(usernameLogin.getValue()) && isStringEmpty(passwordLogin.getValue())) {
            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.MISSING_FIELDS,
                            "Please fill all the fields."
                    )
            );
            return;
        }

        if (!getMainDataRepository()
                .getLocalDataRepository()
                .isUserAccountExists(loginDataModel.getUser())) {
            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.LOGIN_FAILED,
                            "User does not exist!"
                    )
            );
            return;
        }

        getMainDataRepository()
                .getLocalDataRepository()
                .loginLiveData(
                        usernameLogin.getValue(),
                        passwordLogin.getValue()
                );

        liveLoginDataMutableLiveData.setValue(
                new LiveLoginData(
                        LoginEnumState.LOGIN_SUCCESSFUL,
                        "Login Success"
                )
        );
    }

    @Override
    public void onUserRegistration() {
        Timber.e(TAG, "OnRegisterButtonClicked");

        if (isStringEmpty(userNameRegistration.getValue())
                || isStringEmpty(passwordRegistration.getValue())
                || isStringEmpty(emailRegistration.getValue())
                || isStringEmpty(confirmPasswordRegistration.getValue())) {

            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.MISSING_FIELDS,
                            "Please fill up all the fields."
                    )
            );

            return;
        }

        if (!isValidMail(emailRegistration.getValue())) {

            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.INVALID_EMAIL,
                            "Invalid email address"
                    )
            );

            return;
        }

        Timber.e(TAG, "isUserAccountAlreadyExists: " + isUserAccountAlreadyExists());

        if (isUserAccountAlreadyExists()) {

            setFieldsRegistration(
                    emailRegistration.getValue(),
                    userNameRegistration.getValue(),
                    passwordRegistration.getValue(),
                    confirmPasswordRegistration.getValue()
            );

            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.ACCOUNT_ALREADY_EXISTS,
                            "Account already exist"
                    )
            );
            return;
        }

        if (!isStringEmpty(passwordRegistration.getValue())
                && !isStringEmpty(confirmPasswordRegistration.getValue())
                && !passwordRegistration.getValue().equals(confirmPasswordRegistration.getValue())) {

            setFieldsRegistration(
              emailRegistration.getValue(),
              userNameRegistration.getValue(),
              "",
              ""
            );

            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.PASSWORD_NOT_MATCH,
                            "Password not match!"
                    )
            );
            return;
        }

        getMainDataRepository()
                .getLocalDataRepository()
                .insertLogin(new LoginEntity(0,
                                userNameRegistration.getValue(),
                                emailRegistration.getValue(),
                                passwordRegistration.getValue(),
                                confirmPasswordRegistration.getValue()
                        )
                );

        liveLoginDataMutableLiveData.setValue(
                new LiveLoginData(
                        LoginEnumState.REGISTRATION_SUCCESS,
                        "Registration Successful"
                )
        );



    }

    private void setFieldsRegistration(String email, String user, String pass, String confirmPass) {
        emailRegistration.setValue(email);
        userNameRegistration.setValue(user);
        passwordRegistration.setValue(pass);
        confirmPasswordRegistration.setValue(confirmPass);
    }

    private boolean isUserAccountAlreadyExists() {
        return getMainDataRepository()
                .getLocalDataRepository().isUserAccountExists(
                        userNameRegistration.getValue());
    }


}
