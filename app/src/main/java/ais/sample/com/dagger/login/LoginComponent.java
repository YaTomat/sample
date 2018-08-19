package ais.sample.com.dagger.login;

import javax.inject.Singleton;

import ais.sample.com.dagger.FragmentScope;
import ais.sample.com.dagger.common.ApplicationComponent;
import ais.sample.com.login.presentation.LoginSellerFragment;
import ais.sample.com.login.presentation.LoginTerminalFragment;
import dagger.Component;

/**
 * Created by YaTomat on 10.06.2017.
 */

@Component(
        modules = LoginModule.class,
        dependencies = {ApplicationComponent.class})
@FragmentScope
public interface LoginComponent {

    void inject(LoginTerminalFragment loginTerminalFragment);

    void inject(LoginSellerFragment loginSellerFragment);
}
