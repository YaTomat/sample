package ais.sample.com.dagger.common;

import android.app.Application;
import android.content.Context;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import ais.sample.com.MainActivity;
import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseStatelessPresenter;
import dagger.Component;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

/**
 * Created by YaTomat on 12.06.2017.
 */
@Component(
        modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
    void inject(Application application);

    void inject(MainActivity mainActivity);

    Context context();

    @Named("ui")
    Scheduler uiScheduler();

    @Named("work")
    Scheduler workScheduler();

    Retrofit retrofit();

    AccessController accessController();

    Map<String, BaseStatelessPresenter> presenterMap();
}
