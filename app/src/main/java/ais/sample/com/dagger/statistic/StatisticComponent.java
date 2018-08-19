package ais.sample.com.dagger.statistic;

import ais.sample.com.dagger.FragmentScope;
import ais.sample.com.dagger.common.ApplicationComponent;
import ais.sample.com.statistic.presentation.StatisticFragment;
import dagger.Component;

/**
 * Created by YaTomat on 06.09.2017.
 */
@Component(
        modules = StatisticModule.class,
        dependencies = {ApplicationComponent.class})
@FragmentScope
public interface StatisticComponent {

    void inject(StatisticFragment fragment);
}
