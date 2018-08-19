package ais.sample.com.login.presentation;

import ais.sample.com.common.ErrorHandler;
import ais.sample.com.common.ProgressController;
import ais.sample.com.common.StatelessView;
import ais.sample.com.login.data.LoginResponse;
import io.reactivex.Observable;

/**
 * Created by YaTomat on 12.06.2017.
 */

public interface LoginView extends ErrorHandler, ProgressController, StatelessView {

    Observable<Object> onLoginClick();

    Observable<String> onPasswordChanged();

    Observable<String> onLoginChanged();

    void successLogin(LoginResponse loginResponse);

}
