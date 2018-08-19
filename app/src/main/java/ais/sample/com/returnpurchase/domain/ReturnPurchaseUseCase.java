package ais.sample.com.returnpurchase.domain;

import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseUseCase;
import ais.sample.com.returnpurchase.data.ReturnPurchaseApi;
import ais.sample.com.returnpurchase.data.ReturnPurchaseRequest;
import ais.sample.com.returnpurchase.data.ReturnPurchaseResponse;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by YaTomat on 13.09.2017.
 */

public class ReturnPurchaseUseCase extends BaseUseCase<ReturnPurchaseRequest, ReturnPurchaseResponse> {

    private final ReturnPurchaseApi returnPurchaseApi;
    private final AccessController accessController;

    public ReturnPurchaseUseCase(Scheduler uiScheduler, Scheduler workerScheduler, ReturnPurchaseApi returnPurchaseApi, AccessController accessController) {
        super(uiScheduler, workerScheduler);
        this.returnPurchaseApi = returnPurchaseApi;
        this.accessController = accessController;
    }

    @Override
    protected Observable<ReturnPurchaseResponse> buildObservable(ReturnPurchaseRequest inputData) {
        return returnPurchaseApi.returnPurchase(accessController.getAccessToken(), inputData.idSale).map((responseBody -> new ReturnPurchaseResponse()));
    }
}
