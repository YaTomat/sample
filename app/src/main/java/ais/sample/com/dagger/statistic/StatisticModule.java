package ais.sample.com.dagger.statistic;

import java.util.Map;

import javax.inject.Named;

import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.statistic.data.StatisticApi;
import ais.sample.com.statistic.domain.GetStatisticsUseCase;
import ais.sample.com.statistic.presentation.StatisticPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

/**
 * Created by YaTomat on 06.09.2017.
 */
@Module
public class StatisticModule {

    @Provides
    public StatisticApi provideStatisticApi(Retrofit retrofit) {
        return retrofit.create(StatisticApi.class);
    }

    @Provides
    public GetStatisticsUseCase provideGetStatisticsUseCase(StatisticApi statisticApi,
                                                            @Named("ui") Scheduler ui, @Named("work") Scheduler work,
                                                            AccessController accessController) {
        return new GetStatisticsUseCase(ui, work, statisticApi, accessController);
    }

    @Provides
    public StatisticPresenter provideStatisticPresenter(GetStatisticsUseCase getStatisticsUseCase,
                                                        Map<String, BaseStatelessPresenter> statelessPresenterMap) {
        BaseStatelessPresenter baseStatelessPresenter = statelessPresenterMap.get(StatisticPresenter.class.getSimpleName());
        if (baseStatelessPresenter == null) {
            return new StatisticPresenter(getStatisticsUseCase);
        } else {
            return (StatisticPresenter) baseStatelessPresenter;
        }
    }
}
