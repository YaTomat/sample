package ais.sample.com.statistic.presentation;

import android.os.Bundle;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ais.sample.com.R;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.ErrorHandlingObserver;
import ais.sample.com.statistic.data.StatisticRequest;
import ais.sample.com.statistic.data.StatisticsResponse;
import ais.sample.com.statistic.domain.GetStatisticsUseCase;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by YaTomat on 30.08.2017.
 */

public class StatisticPresenter extends BaseStatelessPresenter<StatisticView> {

    private final GetStatisticsUseCase getStatisticUseCase;

    private CompositeDisposable subscriptions;
    private String endDate;
    private String startDate;
    private boolean terminalStatistic;
    private StatisticsResponse statisticsResponse;

    public StatisticPresenter(GetStatisticsUseCase getStatisticsUseCase) {
        this.getStatisticUseCase = getStatisticsUseCase;
    }

    @Override
    public void onCreated(StatisticView view, Bundle savedInstance) {
        super.onCreated(view, savedInstance);
        subscriptions = new CompositeDisposable();
        subscriptions.add(
                getView().onEndDateChanged().subscribe((endDate -> {
                    StatisticPresenter.this.endDate = endDate;
                    verifyInputData();
                })));
        subscriptions.add(
                getView().onStartDateChanged().subscribe((startDate -> {
                    StatisticPresenter.this.startDate = startDate;
                    verifyInputData();
                })));
        subscriptions.add(
                getView().onTypeReportChanged().subscribe((isTerminal -> {
                    StatisticPresenter.this.terminalStatistic = isTerminal;
                    verifyInputData();
                })));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (statisticsResponse != null) {
            getView().updateView(statisticsResponse);
            getView().stopProgress();
        }
    }

    @Override
    public void onDestroyed() {
        if (!isRestoreNeeded()) {
            subscriptions.dispose();
            subscriptions.clear();
            getView().removePresenter();
        }
        super.onDestroyed();
    }

    private void verifyInputData() {
        if (!TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(startDate)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            Date convertedStartDate = new Date();
            Date convertedEndDate = new Date();
            try {
                convertedStartDate = dateFormat.parse(startDate);
                convertedEndDate = dateFormat.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (convertedStartDate.after(convertedEndDate)) {
                if (isViewAvailable()) {
                    getView().handleError(R.string.statistic_please_choose_correct_end_date);
                }
            } else {
                StatisticRequest inputData = new StatisticRequest(endDate, startDate, terminalStatistic);
                statisticsResponse = null;
                if (isViewAvailable()) {
                    getView().showProgress();
                }
                getStatisticUseCase.subscribe(inputData, new ErrorHandlingObserver<StatisticsResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull StatisticsResponse statisticsResponse) {
                        StatisticPresenter.this.statisticsResponse = statisticsResponse;
                        if (isViewAvailable()) {
                            getView().updateView(statisticsResponse);
                            getView().stopProgress();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (isViewAvailable()) {
                            super.handleError(e);
                            getView().stopProgress();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }
    }
}
