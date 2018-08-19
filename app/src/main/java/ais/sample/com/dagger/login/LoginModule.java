package ais.sample.com.dagger.login;

import android.content.Context;

import java.util.Map;

import javax.inject.Named;

import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.dagger.FragmentScope;
import ais.sample.com.login.data.LoginApi;
import ais.sample.com.login.domain.LoginUseCase;
import ais.sample.com.login.presentation.LoginSellerPresenter;
import ais.sample.com.login.presentation.LoginTerminalPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

/**
 * Created by YaTomat on 10.06.2017.
 */

@Module
public class LoginModule {

    @Provides
    LoginApi provideLoginApi(Retrofit retrofit) {
        return retrofit.create(LoginApi.class);
    }

    @Provides
    @FragmentScope
    LoginSellerPresenter provideLoginSellerPresenter(LoginUseCase loginUseCase, Map<String, BaseStatelessPresenter> statelessPresenterMap, Context context) {
        BaseStatelessPresenter loginSellerPresenter = statelessPresenterMap.get(LoginSellerPresenter.class.getSimpleName());
        if (loginSellerPresenter == null) {
            return new LoginSellerPresenter(loginUseCase, context);
        } else {
            return (LoginSellerPresenter) loginSellerPresenter;
        }
    }

    @Provides
    LoginTerminalPresenter provideLoginTerminalPresenter() {
        return new LoginTerminalPresenter();
    }

    @Provides
    @FragmentScope
    LoginUseCase provideLoginUseCase(LoginApi loginApi, @Named("ui") Scheduler ui, @Named("work") Scheduler work) {
        return new LoginUseCase(ui, work, loginApi);
    }

}
