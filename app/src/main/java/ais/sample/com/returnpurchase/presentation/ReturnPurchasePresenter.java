package ais.sample.com.returnpurchase.presentation;

import android.os.Bundle;

import java.util.ArrayList;

import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.ErrorHandlingObserver;
import ais.sample.com.returnpurchase.data.ReturnPurchaseRequest;
import ais.sample.com.returnpurchase.data.ReturnPurchaseResponse;
import ais.sample.com.returnpurchase.domain.ReturnPurchaseUseCase;
import ais.sample.com.statistic.data.StatisticItem;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by YaTomat on 13.09.2017.
 */

public class ReturnPurchasePresenter extends BaseStatelessPresenter<ReturnPurchaseView> implements ReturnPurchaseAdapter.OnReturnClickListener {
    private final ReturnPurchaseUseCase returnPurchaseUseCase;

    private ArrayList<StatisticItem> purchaseList;
    private CompositeDisposable subscriptions;

    public ReturnPurchasePresenter(ReturnPurchaseUseCase returnPurchaseUseCase) {
        this.returnPurchaseUseCase = returnPurchaseUseCase;
    }

    @Override
    public void onCreated(ReturnPurchaseView view, Bundle savedInstance) {
        super.onCreated(view, savedInstance);
        subscriptions = new CompositeDisposable();
    }

    public void setPurchaseList(ArrayList<StatisticItem> purchaseList) {
        this.purchaseList = purchaseList;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (purchaseList != null) {
            getView().stopProgress();
            getView().updateView(purchaseList);
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

    @Override
    public void onReturnClick(int idSale) {
        if (isViewAvailable()) {
            getView().showProgress();
            ReturnPurchaseRequest inputData = new ReturnPurchaseRequest(idSale);
            returnPurchaseUseCase.subscribe(inputData, new ErrorHandlingObserver<ReturnPurchaseResponse>(getView()) {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull ReturnPurchaseResponse returnPurchaseResponse) {
                    ArrayList<StatisticItem> secondList = new ArrayList<>();
                    for (StatisticItem a : purchaseList) {
                        if (a.saleId != idSale) {
                            secondList.add(a);
                        }
                    }
                    ReturnPurchasePresenter.this.purchaseList = secondList;
                    if (isViewAvailable()) {
                        getView().stopProgress();
                        getView().updateView(ReturnPurchasePresenter.this.purchaseList);
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    if (isViewAvailable()) {
                        getView().stopProgress();
                        super.handleError(e);
                    }
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }
}
