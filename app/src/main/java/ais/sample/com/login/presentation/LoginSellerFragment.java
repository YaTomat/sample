package ais.sample.com.login.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import javax.inject.Inject;

import ais.sample.com.MainActivity;
import ais.sample.com.R;
import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.Constants;
import ais.sample.com.common.FullscreenProgressDialogFragment;
import ais.sample.com.login.data.LoginResponse;
import butterknife.BindView;

import static ais.sample.com.common.FragmentUtils.show;

/**
 * Created by YaTomat on 09.07.2017.
 */

public class LoginSellerFragment extends BaseLoginFragment {

    public static final String ARG_TERMINAL_TOKEN = "ARG_TERMINAL_TOKEN";

    @Inject
    LoginSellerPresenter presenter;
    @BindView(R.id.tv_change_terminal)
    TextView tvChangeTerminal;
    @Inject
    AccessController accessController;
    @Inject
    Map<String, BaseStatelessPresenter> statelessPresenterMap;
    private FullscreenProgressDialogFragment dialogFragment;


    public static LoginSellerFragment newInstance() {

        Bundle args = new Bundle();
        LoginSellerFragment fragment = new LoginSellerFragment();
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
        presenter.onCreated(this, savedInstanceState);
        String terminalLogin;
        String terminalPassword;
        terminalLogin = getContext().getSharedPreferences(Constants.PREFERENCES_TERMINAL, Context.MODE_PRIVATE)
                .getString(Constants.PREFERENCES_TERMINAL_LOGIN, null);
        terminalPassword = getContext().getSharedPreferences(Constants.PREFERENCES_TERMINAL, Context.MODE_PRIVATE)
                .getString(Constants.PREFERENCES_TERMINAL_PASSWORD, null);
        presenter.setTerminalData(terminalLogin, terminalPassword);
        SpannableString changeTerminalText = new SpannableString(getString(R.string.login_change_terminal, terminalLogin));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                clearTerminalData();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        changeTerminalText.setSpan(clickableSpan, terminalLogin.length() + 15, terminalLogin.length() + 15 + 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvChangeTerminal.setText(changeTerminalText);
        tvChangeTerminal.setMovementMethod(LinkMovementMethod.getInstance());
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstance();
        statelessPresenterMap.put(LoginSellerPresenter.class.getSimpleName(), presenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroyed();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_seller;
    }

    @Override
    public void successLogin(LoginResponse loginResponse) {
        accessController.setLoginResponse(loginResponse);
        Intent intent = new Intent(getContext(), MainActivity.class);
        clearTerminalData();
        startActivity(intent);
        getActivity().finish();
    }

    private void clearTerminalData() {
        getContext().getSharedPreferences(Constants.PREFERENCES_TERMINAL, Context.MODE_PRIVATE)
                .edit()
                .remove(Constants.PREFERENCES_TERMINAL_LOGIN)
                .apply();
        getContext().getSharedPreferences(Constants.PREFERENCES_TERMINAL, Context.MODE_PRIVATE)
                .edit()
                .remove(Constants.PREFERENCES_TERMINAL_PASSWORD)
                .apply();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_login_container, LoginTerminalFragment.newInstance())
                .commit();
    }

    @Override
    public void handleError(String errorMessage) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(getString(R.string.login_error_title))
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                })
                .show();
    }

    @Override
    public void showProgress() {
        dialogFragment = FullscreenProgressDialogFragment.newInstance();
        show(getActivity().getSupportFragmentManager(), dialogFragment);
    }

    @Override
    public void stopProgress() {
        if (dialogFragment != null && !getActivity().getSupportFragmentManager().isDestroyed()) {
            dialogFragment.dismissAllowingStateLoss();
        }
    }

    @Override
    public void removePresenter() {
        statelessPresenterMap.remove(LoginSellerPresenter.class.getSimpleName());
    }
}
