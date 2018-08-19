package ais.sample.com.statistic.domain;

import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseUseCase;
import ais.sample.com.statistic.data.StatisticApi;
import ais.sample.com.statistic.data.StatisticRequest;
import ais.sample.com.statistic.data.StatisticsResponse;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by YaTomat on 30.08.2017.
 */

public class GetStatisticsUseCase extends BaseUseCase<StatisticRequest, StatisticsResponse> {

    private final AccessController accessController;
    private final StatisticApi statisticsApi;

    public GetStatisticsUseCase(Scheduler uiScheduler, Scheduler workerScheduler,
                                StatisticApi statisticApi, AccessController accessController) {
        super(uiScheduler, workerScheduler);
        this.statisticsApi = statisticApi;
        this.accessController = accessController;
    }

    @Override
    protected Observable<StatisticsResponse> buildObservable(StatisticRequest inputData) {
        return statisticsApi.getStatistic(accessController.getAccessToken(),
                inputData.startDate, inputData.endDate, inputData.isTerminal);
    }
}
