package ais.sample.com.purchase.presentation;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ais.sample.com.R;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.ErrorHandlingObserver;
import ais.sample.com.login.data.Action;
import ais.sample.com.purchase.data.TransactionRequest;
import ais.sample.com.purchase.data.TransactionResponse;
import ais.sample.com.purchase.domain.GetStocksUseCase;
import ais.sample.com.purchase.domain.TransactionUseCase;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static ais.sample.com.common.PhoneUtil.isValidPhone;

/**
 * Created by YaTomat on 01.07.2017.
 */

public class PurchasePresenter extends BaseStatelessPresenter<PurchaseView> {

    public static final String TAG = PurchasePresenter.class.getSimpleName();

    private final GetStocksUseCase getStocksUseCase;
    private final TransactionUseCase transactionUseCase;

    private CompositeDisposable subscriptions;
    private String cardNumber;
    private String transactionAmount;
    private Integer stockId;
    private List<Action> stocks;
    private String phoneNumber;

    public PurchasePresenter(GetStocksUseCase getStocksUseCase, TransactionUseCase transactionUseCase) {
        this.getStocksUseCase = getStocksUseCase;
        this.transactionUseCase = transactionUseCase;
    }

    @Override
    public void onCreated(PurchaseView view, Bundle savedInstance) {
        super.onCreated(view, savedInstance);
        subscriptions = new CompositeDisposable();
        subscriptions.add(getView()
                .onCardNumberChanged()
                .subscribe((cardNumber) -> this.cardNumber = cardNumber));
        subscriptions.add(getView()
                .onPurchaseAmountChanged()
                .subscribe((transactionAmount) -> this.transactionAmount = transactionAmount));
        subscriptions.add(getView()
                .onPhoneNumberChanged()
                .subscribe((phoneNumber) -> this.phoneNumber = phoneNumber));
        subscriptions.add(getView()
                .onStockChanged()
                .subscribe((stockId) -> this.stockId = stocks != null && stockId != -1 ? stocks.get(stockId).id : 0));
        subscriptions.add(getView()
                .onPerformTransactionPress()
                .subscribe((obj) -> {
                    if (stockId != null && (!TextUtils.isEmpty(cardNumber) || (!TextUtils.isEmpty(phoneNumber) && isValidPhone(phoneNumber))) && !TextUtils.isEmpty(transactionAmount)) {
                        getView().showProgress();
                        transactionUseCase.subscribe(new TransactionRequest(cardNumber, transactionAmount, stockId.toString(), phoneNumber),
                                new ErrorHandlingObserver<TransactionResponse>(view) {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull TransactionResponse transactionResponse) {
                                        if (isViewAvailable()) {
                                            getView().onTransactionFinished(transactionResponse);
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
                    } else {
                        if (isViewAvailable()) {
                            getView().handleError(R.string.not_all_fields_are_filled);
                        }
                    }
                }));
        getStocksUseCase.subscribe(new Object(), new Observer<List<Action>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Action> stockResponse) {
                PurchasePresenter.this.stocks = stockResponse;
                Log.d(TAG, "onNext: " + stockResponse);
                if (isViewAvailable()) {
                    List<String> stocks = new ArrayList<>();
                    for (Action action : stockResponse) {
                        stocks.add(action.name);
                    }
                    getView().updateStocks(stocks);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (stocks != null) {
            List<String> stocks = new ArrayList<>();
            for (Action action : this.stocks) {
                stocks.add(action.name);
            }
            getView().updateStocks(stocks);
        }
        if (!TextUtils.isEmpty(cardNumber)) {
            getView().updateCardNumber(cardNumber);
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

    void onCameraResult(String contents) {
        this.cardNumber = contents;
        if (isViewAvailable()) {
            getView().updateCardNumber(contents);
        }
    }
}
