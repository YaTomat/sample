package ais.sample.com.cardinfo.domain;

import ais.sample.com.cardinfo.data.CardInfoApi;
import ais.sample.com.cardinfo.data.CardInfoRequest;
import ais.sample.com.cardinfo.data.CardInfoResponse;
import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseUseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by YaTomat on 10.09.2017.
 */

public class GetCardInfoUseCase extends BaseUseCase<CardInfoRequest, CardInfoResponse> {

    private final AccessController accessController;
    private final CardInfoApi cardInfoApi;

    public GetCardInfoUseCase(Scheduler uiScheduler, Scheduler workerScheduler, AccessController accessController, CardInfoApi cardInfoApi) {
        super(uiScheduler, workerScheduler);
        this.accessController = accessController;
        this.cardInfoApi = cardInfoApi;
    }

    @Override
    protected Observable<CardInfoResponse> buildObservable(CardInfoRequest inputData) {
        return cardInfoApi.getCardInfo(accessController.getAccessToken(), inputData.cardNumber, inputData.phoneNumber);
    }
}
