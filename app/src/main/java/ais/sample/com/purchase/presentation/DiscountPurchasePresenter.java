package ais.sample.com.purchase.presentation;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.Locale;

import ais.sample.com.R;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.ErrorHandlingObserver;
import ais.sample.com.purchase.data.DiscountApplyRequest;
import ais.sample.com.purchase.data.DiscountApplyResponse;
import ais.sample.com.purchase.data.TransactionResponse;
import ais.sample.com.purchase.domain.DiscountApplyUseCase;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by YaTomat on 29.08.2017.
 */

public class DiscountPurchasePresenter extends BaseStatelessPresenter<DiscountPurchaseView> {

    private final DiscountApplyUseCase discountApplyUseCase;
    private CompositeDisposable subscriptions;
    private TransactionResponse transactionResponse;
    private String writingOffBonuses;
    private DiscountApplyResponse discountApplyResponse;

    public DiscountPurchasePresenter(DiscountApplyUseCase discountApplyUseCase) {
        this.discountApplyUseCase = discountApplyUseCase;
    }

    @Override
    public void onCreated(DiscountPurchaseView view, Bundle savedInstance) {
        super.onCreated(view, savedInstance);
        subscriptions = new CompositeDisposable();
        subscriptions.add(getView()
                .onWritingOffBonusesChanged()
                .filter((writingOffBonuses) -> {
                    if (isSuitableBonuses(writingOffBonuses)) {
                        if (isViewAvailable()) {
                            getView().showMaxDiscountAlert(
                                    String.format(Locale.getDefault(), "%.2f", transactionResponse.bonusesForPayment));
                            getView().setWritingOffDiscountAmount(this.writingOffBonuses);
                        }
                        return false;
                    }
                    return true;
                })
                .subscribe((writingOFfBonuses) -> this.writingOffBonuses = writingOFfBonuses));
        subscriptions.add(getView()
                .onPayBonusesClick()
                .subscribe((obj) -> {
                    if (!TextUtils.isEmpty(writingOffBonuses)) {
                        getView().showProgress();
                        discountApplyUseCase.subscribe(new DiscountApplyRequest(this.writingOffBonuses, transactionResponse.saleId),
                                new ErrorHandlingObserver<DiscountApplyResponse>(view) {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull DiscountApplyResponse discountApplyResponse) {
                                        if (isViewAvailable()) {
                                            updateView(discountApplyResponse);
                                        } else {
                                            DiscountPurchasePresenter.this.discountApplyResponse = discountApplyResponse;
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
    }

    @Override
    public void onStart() {
        super.onStart();
        if (discountApplyResponse != null) {
            updateView(discountApplyResponse);
        }
    }

    private void updateView(@NonNull DiscountApplyResponse discountApplyResponse) {
        getView().stopProgress();
        getView().finishWritingOffDiscount(discountApplyResponse);
    }

    private boolean isSuitableBonuses(String writingOffBonuses) {
        return !TextUtils.isEmpty(writingOffBonuses) &&
                Float.parseFloat(writingOffBonuses) > transactionResponse.bonusesForPayment;
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

    public void setTransactionResponse(TransactionResponse transactionResponse) {
        this.transactionResponse = transactionResponse;
    }
}
