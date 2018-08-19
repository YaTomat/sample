package ais.sample.com.login.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import ais.sample.com.R;
import ais.sample.com.common.Constants;
import ais.sample.com.login.data.LoginResponse;

/**
 * Created by YaTomat on 09.07.2017.
 */

public class LoginTerminalFragment extends BaseLoginFragment implements LoginTerminalView {

    @Inject
    LoginTerminalPresenter presenter;

    public static LoginTerminalFragment newInstance() {

        Bundle args = new Bundle();

        LoginTerminalFragment fragment = new LoginTerminalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjectionComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void successLogin(LoginResponse loginResponse) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_terminal;
    }

    @Override
    public void onInputFinished(String login, String password) {
        getContext().getSharedPreferences(Constants.PREFERENCES_TERMINAL, Context.MODE_PRIVATE)
                .edit()
                .putString(Constants.PREFERENCES_TERMINAL_PASSWORD, password)
                .putString(Constants.PREFERENCES_TERMINAL_LOGIN, login)
                .apply();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_login_container, LoginSellerFragment.newInstance())
                .commit();
    }
}
