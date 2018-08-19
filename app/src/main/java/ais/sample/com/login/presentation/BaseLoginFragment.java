package ais.sample.com.login.presentation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import ais.sample.com.MyApplication;
import ais.sample.com.R;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.dagger.login.DaggerLoginComponent;
import ais.sample.com.dagger.login.LoginComponent;
import ais.sample.com.dagger.login.LoginModule;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by YaTomat on 09.07.2017.
 */

public abstract class BaseLoginFragment extends Fragment implements LoginView {

    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private LoginComponent injectionComponent;


    @LayoutRes
    protected abstract int getLayoutId();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void handleError() {

    }

    @Override
    public void handleError(@StringRes int errorMessage) {
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

    }

    @Override
    public void showProgress(@StringRes int message) {

    }

    @Override
    public void stopProgress() {

    }

    @Override
    public Observable<Object> onLoginClick() {
        return RxView.clicks(btnLogin);
    }

    @Override
    public Observable<String> onPasswordChanged() {
        return RxTextView.textChanges(etPassword).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<String> onLoginChanged() {
        return RxTextView.textChanges(etLogin).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    protected LoginComponent setUpInjectionComponent() {
        if (injectionComponent == null) {
            injectionComponent = DaggerLoginComponent.builder()
                    .applicationComponent(MyApplication.getComponent())
                    .loginModule(new LoginModule())
                    .build();
        }
        return injectionComponent;
    }

    @Override
    public void removePresenter() {

    }
}
