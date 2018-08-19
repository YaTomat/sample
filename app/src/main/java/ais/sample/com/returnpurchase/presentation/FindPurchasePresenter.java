package ais.sample.com.returnpurchase.presentation;

import android.os.Bundle;
import android.text.TextUtils;

import ais.sample.com.R;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.ErrorHandlingObserver;
import ais.sample.com.returnpurchase.data.FindPurchaseRequest;
import ais.sample.com.returnpurchase.data.FindPurchaseResponse;
import ais.sample.com.returnpurchase.domain.FindPurchaseUseCase;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static ais.sample.com.common.PhoneUtil.isValidPhone;

/**
 * Created by YaTomat on 12.09.2017.
 */

public class FindPurchasePresenter extends BaseStatelessPresenter<FindPurchaseView> {
    private final FindPurchaseUseCase findPurchaseUseCase;

    private CompositeDisposable subscriptions;
    private String cardNumber;
    private String phoneNumber;
    private String amountPurchase;
    private String datePurchase;
    private FindPurchaseResponse findPurchaseResponse;

    public FindPurchasePresenter(FindPurchaseUseCase findPurchaseUseCase) {
        this.findPurchaseUseCase = findPurchaseUseCase;
    }

    @Override
    public void onCreated(FindPurchaseView view, Bundle savedInstance) {
        super.onCreated(view, savedInstance);
        subscriptions = new CompositeDisposable();
        subscriptions.add(getView()
                .getCardNumber()
                .subscribe((cardNumber) -> this.cardNumber = cardNumber));
        subscriptions.add(getView()
                .getPhoneNumber()
                .subscribe((phoneNumber) -> this.phoneNumber = phoneNumber));
        subscriptions.add(getView()
                .getPurchaseDate()
                .subscribe((purchaseDate) -> this.datePurchase = purchaseDate));
        subscriptions.add(getView()
                .getAmountPurchase()
                .subscribe((amount) -> this.amountPurchase = amount));
        subscriptions.add(getView()
                .onFindClick()
                .subscribe((obj) -> {
                    if (!TextUtils.isEmpty(cardNumber) || (!TextUtils.isEmpty(phoneNumber))&& isValidPhone(phoneNumber)) {
                        FindPurchaseRequest findPurchaseRequest = new FindPurchaseRequest(cardNumber, phoneNumber, amountPurchase, datePurchase == null ? "null" : datePurchase);
                        getView().showProgress();
                        findPurchaseUseCase.subscribe(findPurchaseRequest, new ErrorHandlingObserver<FindPurchaseResponse>(getView()) {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull FindPurchaseResponse findPurchaseResponse) {
                                FindPurchasePresenter.this.findPurchaseResponse = findPurchaseResponse;
                                if (isViewAvailable()) {
                                    FindPurchasePresenter.this.findPurchaseResponse = null;
                                    getView().stopProgress();
                                    getView().updateView(findPurchaseResponse);
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
                        getView().handleError(R.string.not_all_fields_are_filled);
                    }
                }));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (findPurchaseResponse != null) {
            getView().stopProgress();
            findPurchaseResponse = null;
            getView().updateView(findPurchaseResponse);
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
}
