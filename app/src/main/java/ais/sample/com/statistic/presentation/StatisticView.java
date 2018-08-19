package ais.sample.com.statistic.presentation;

import ais.sample.com.common.ErrorHandler;
import ais.sample.com.common.ProgressController;
import ais.sample.com.common.StatelessView;
import ais.sample.com.statistic.data.StatisticsResponse;
import io.reactivex.Observable;

/**
 * Created by YaTomat on 30.08.2017.
 */

interface StatisticView extends ErrorHandler, ProgressController, StatelessView {

    Observable<String> onStartDateChanged();

    Observable<String> onEndDateChanged();

    Observable<Boolean> onTypeReportChanged();

    void updateView(StatisticsResponse statisticsResponse);
}
