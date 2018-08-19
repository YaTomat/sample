package ais.sample.com.common;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;

/**
 * Created by YaTomat on 12.06.2017.
 */

public abstract class BaseUseCase<GT, T> {

    private final Scheduler uiScheduler;
    private final Scheduler workerScheduler;

    public BaseUseCase(Scheduler uiScheduler, Scheduler workerScheduler) {
        this.uiScheduler = uiScheduler;
        this.workerScheduler = workerScheduler;
    }

    public void subscribe(GT inputData, Observer<T> subscriber) {
        buildObservable(inputData)
                .subscribeOn(workerScheduler)
                .observeOn(uiScheduler)
                .subscribe(subscriber);
    }

    protected abstract Observable<T> buildObservable(GT inputData);
}
