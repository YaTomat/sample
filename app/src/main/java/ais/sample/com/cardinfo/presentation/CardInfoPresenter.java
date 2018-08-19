package ais.sample.com.cardinfo.presentation;

import android.os.Bundle;
import android.text.TextUtils;

import ais.sample.com.R;
import ais.sample.com.cardinfo.data.CardInfoRequest;
import ais.sample.com.cardinfo.data.CardInfoResponse;
import ais.sample.com.cardinfo.domain.GetCardInfoUseCase;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.ErrorHandlingObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by YaTomat on 10.09.2017.
 */

public class CardInfoPresenter extends BaseStatelessPresenter<CardInfoView> {

    private final GetCardInfoUseCase getCardInfoUseCase;

    private CardInfoResponse cardInfoResponse;
    private String cardNumber;
    private CompositeDisposable subscriptions;

    public CardInfoPresenter(GetCardInfoUseCase getCardInfoUseCase) {
        this.getCardInfoUseCase = getCardInfoUseCase;
    }

    @Override
    public void onCreated(CardInfoView view, Bundle savedInstance) {
        super.onCreated(view, savedInstance);
        subscriptions = new CompositeDisposable();
        subscriptions.add(
                getView().onCardNumberChanged().subscribe((cardNumber -> {
                    CardInfoPresenter.this.cardNumber = cardNumber;
                })));
        subscriptions.add(
                getView().onGetInfoClick().subscribe((object -> {
                    if (!TextUtils.isEmpty(cardNumber)) {
                        getView().showProgress();
                        getCardInfoUseCase.subscribe(new CardInfoRequest(cardNumber, "null"),
                                new ErrorHandlingObserver<CardInfoResponse>(getView()) {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull CardInfoResponse cardInfoResponse) {
                                        CardInfoPresenter.this.cardInfoResponse = cardInfoResponse;
                                        if (isViewAvailable()) {
                                            getView().updateView(cardInfoResponse);
                                            getView().stopProgress();
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        if (!handleError(e)) {
                                            if (isViewAvailable()) {
                                                getView().stopProgress();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    } else {
                        if (isViewAvailable()) {
                            getView().handleError(R.string.card_info_error_message);
                        }
                    }
                })));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (cardInfoResponse != null) {
            getView().updateView(cardInfoResponse);
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
}
