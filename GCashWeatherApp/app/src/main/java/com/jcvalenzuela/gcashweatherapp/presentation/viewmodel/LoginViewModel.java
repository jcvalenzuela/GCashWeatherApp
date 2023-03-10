package com.jcvalenzuela.gcashweatherapp.presentation.viewmodel;

import static com.jcvalenzuela.gcashweatherapp.domain.utils.Utility.isStringEmpty;
import static com.jcvalenzuela.gcashweatherapp.domain.utils.Utility.isValidMail;

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
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends BaseViewModel implements LoginHelper {

    //RxDisposable
    private Disposable rxDisposable = new CompositeDisposable();
    public MutableLiveData<String> emailRegistration = new MutableLiveData<>();
    public MutableLiveData<String> userNameRegistration = new MutableLiveData<>();
    public MutableLiveData<String> passwordRegistration = new MutableLiveData<>();
    public MutableLiveData<String> confirmPasswordRegistration = new MutableLiveData<>();
    public MutableLiveData<String> usernameLogin = new MutableLiveData<>();
    public MutableLiveData<String> passwordLogin = new MutableLiveData<>();

    private LoginDataModel loginDataModel;

    private MutableLiveData<LiveLoginData> liveLoginDataMutableLiveData = new MutableLiveData<>();

    public LiveData<LiveLoginData> getLiveLoginDataLiveData() {
        return liveLoginDataMutableLiveData;
    }

    @Inject
    public LoginViewModel(MainDataRepositoryImpl mainDataRepository) {
        super(mainDataRepository);
        liveLoginDataMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public void onLoginButtonClicked() {
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
    public void onRegisterUserButtonClicked() {
        loginDataModel = new LoginDataModel(
                userNameRegistration.getValue(),
                passwordRegistration.getValue(),
                emailRegistration.getValue(),
                confirmPasswordRegistration.getValue()
        );

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

        if (!isValidMail(loginDataModel.getEmail())) {

            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.INVALID_EMAIL,
                            "Invalid email address"
                    )
            );

            return;
        }

        if (getMainDataRepository()
                .getLocalDataRepository().isAlreadyRegistered(
                        emailRegistration.getValue(),
                        userNameRegistration.getValue())) {
            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.ACCOUNT_ALREADY_EXISTS,
                            "Account already exist"
                    )
            );
        }

        if (isStringEmpty(passwordRegistration.getValue())
                && isStringEmpty(confirmPasswordRegistration.getValue())
                && !passwordRegistration.getValue().equals(confirmPasswordRegistration.getValue())) {
            liveLoginDataMutableLiveData.setValue(
                    new LiveLoginData(
                            LoginEnumState.PASSWORD_NOT_MATCH,
                            "Password not match!"
                    )
            );
        }

        getMainDataRepository()
                .getLocalDataRepository()
                .insertLogin(new LoginEntity(userNameRegistration.getValue(),
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


}
