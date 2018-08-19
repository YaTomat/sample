package ais.sample.com;

import android.app.Application;

import ais.sample.com.dagger.common.ApplicationComponent;
import ais.sample.com.dagger.common.ApplicationModule;
import ais.sample.com.dagger.common.DaggerApplicationComponent;

/**
 * Created by YaTomat on 12.06.2017.
 */

public class MyApplication extends Application {

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        component.inject(this);
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
