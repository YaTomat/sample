package ais.sample.com.returnpurchase.domain;

import java.util.Arrays;

import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseUseCase;
import ais.sample.com.returnpurchase.data.FindPurchaseRequest;
import ais.sample.com.returnpurchase.data.FindPurchaseResponse;
import ais.sample.com.returnpurchase.data.ReturnPurchaseApi;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by YaTomat on 12.09.2017.
 */

public class FindPurchaseUseCase extends BaseUseCase<FindPurchaseRequest, FindPurchaseResponse> {

    private final ReturnPurchaseApi returnPurchaseApi;
    private final AccessController accessController;

    public FindPurchaseUseCase(Scheduler uiScheduler, Scheduler workerScheduler, ReturnPurchaseApi returnPurchaseApi, AccessController accessController) {
        super(uiScheduler, workerScheduler);
        this.returnPurchaseApi = returnPurchaseApi;
        this.accessController = accessController;
    }

    @Override
    protected Observable<FindPurchaseResponse> buildObservable(FindPurchaseRequest inputData) {
        return returnPurchaseApi.findPurchases(accessController.getAccessToken(),
                inputData.cardNumber, inputData.phoneNumber, inputData.date, inputData.amount)
                .map(statisticItems -> new FindPurchaseResponse(Arrays.asList(statisticItems)));
    }
}
