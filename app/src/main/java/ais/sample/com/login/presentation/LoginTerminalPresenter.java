package ais.sample.com.login.presentation;

import android.os.Bundle;

import ais.sample.com.common.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by YaTomat on 09.07.2017.
 */

public class LoginTerminalPresenter extends BasePresenter<LoginTerminalView> {

    private CompositeDisposable subscriptions;
    private String login;
    private String password;

    @Override
    public void onViewCreated(LoginTerminalView view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        subscriptions = new CompositeDisposable();
        subscriptions.add(getView().onLoginClick().subscribe((object) -> {
            view.onInputFinished(login, password);
        }));
        subscriptions.add(getView().onLoginChanged().subscribe((login) -> {
            LoginTerminalPresenter.this.login = login;
        }));
        subscriptions.add(getView().onPasswordChanged().subscribe((password) -> {
            LoginTerminalPresenter.this.password = password;
        }));
    }

    @Override
    public void onViewDestroyed() {
        super.onViewDestroyed();
        subscriptions.dispose();
        subscriptions.clear();
    }

}
