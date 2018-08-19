package ais.sample.com.login.presentation;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import ais.sample.com.R;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.Constants;
import ais.sample.com.common.ErrorHandlingObserver;
import ais.sample.com.login.data.LoginRequest;
import ais.sample.com.login.data.LoginResponse;
import ais.sample.com.login.domain.LoginUseCase;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by YaTomat on 12.06.2017.
 */

public class LoginSellerPresenter extends BaseStatelessPresenter<LoginView> {

    public static final String TAG = LoginSellerPresenter.class.getSimpleName();
    private final LoginUseCase loginUseCase;

    private Context context;
    private CompositeDisposable subscriptions;
    private String terminalPassword;
    private String terminalLogin;
    private String sellerLogin;
    private String sellerPassword;
    private LoginResponse loginResponse;

    public LoginSellerPresenter(LoginUseCase loginUseCase, Context context) {
        this.loginUseCase = loginUseCase;
        this.context = context;
    }

    @Override
    public void onCreated(LoginView view, Bundle savedInstance) {
        super.onCreated(view, savedInstance);
        subscriptions = new CompositeDisposable();
        subscriptions.add(getView()
                .onLoginClick()
                .subscribe((object) -> {
                    if (TextUtils.isEmpty(sellerPassword) || TextUtils.isEmpty(sellerLogin)) {
                        getView().handleError(R.string.not_all_fields_are_filled);
                    } else {
                        view.showProgress();
                        LoginRequest loginRequest = new LoginRequest(terminalLogin, terminalPassword, sellerLogin, sellerPassword);
                        loginUseCase.subscribe(loginRequest, new ErrorHandlingObserver<LoginResponse>(view) {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull LoginResponse loginResponse) {
                                if (loginResponse.actionList != null) {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    try {
                                        String storageValue = objectMapper.writeValueAsString(loginResponse.actionList);
                                        Log.d(TAG, "onNext: " + storageValue);
                                        context.getSharedPreferences(Constants.PREFERENCES_ACTIONS, Context.MODE_PRIVATE)
                                                .edit()
                                                .putString(Constants.PREFERENCES_ACTIONS_KEY, storageValue)
                                                .apply();
                                    } catch (JsonProcessingException e) {
                                        Log.e(TAG, "error parsing actions", e);
                                    }
                                }
                                if (isViewAvailable()) {
                                    updateViewState(loginResponse);
                                } else {
                                    LoginSellerPresenter.this.loginResponse = loginResponse;
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                if (isViewAvailable()) {
                                    if (!super.handleError(e)) {
                                        String errorMessage = "Authorization error";
                                        if (e instanceof HttpException) {
                                            if (((HttpException) e).code() == 401) {
                                                try {
                                                    errorMessage = ((HttpException) e).response().errorBody().string();
                                                } catch (IOException error) {
                                                    error.printStackTrace();
                                                }
                                            }
                                        }
                                        getView().handleError(errorMessage);
                                    }
                                    getView().stopProgress();
                                }
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                    }
                }));
        subscriptions.add(getView()
                .onLoginChanged()
                .subscribe((login) -> this.sellerLogin = login)
        );
        subscriptions.add(getView()
                .onPasswordChanged()
                .subscribe((password) -> this.sellerPassword = password));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (loginResponse != null) {
            updateViewState(loginResponse);
        }
    }

    private void updateViewState(@NonNull LoginResponse loginResponse) {
        if (loginResponse.accessToken != null) {
            getView().successLogin(loginResponse);
        }
        getView().stopProgress();
    }

    @Override
    public void onDestroyed() {
        if (!isRestoreNeeded()) {
            getView().removePresenter();
            subscriptions.dispose();
            subscriptions.clear();
        }
        super.onDestroyed();
    }

    void setTerminalData(String terminalLogin, String terminalPassword) {
        this.terminalLogin = terminalLogin;
        this.terminalPassword = terminalPassword;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
