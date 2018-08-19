package ais.sample.com.purchase.domain;

import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseUseCase;
import ais.sample.com.purchase.data.DiscountApplyRequest;
import ais.sample.com.purchase.data.DiscountApplyResponse;
import ais.sample.com.purchase.data.TransactionApi;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by YaTomat on 29.08.2017.
 */

public class DiscountApplyUseCase extends BaseUseCase<DiscountApplyRequest, DiscountApplyResponse> {

    private final TransactionApi transactionApi;
    private final AccessController accessController;

    public DiscountApplyUseCase(Scheduler uiScheduler, Scheduler workerScheduler, TransactionApi transactionApi, AccessController accessController) {
        super(uiScheduler, workerScheduler);
        this.transactionApi = transactionApi;
        this.accessController = accessController;
    }

    @Override
    protected Observable<DiscountApplyResponse> buildObservable(DiscountApplyRequest inputData) {
        return transactionApi.payByBonuses(accessController.getAccessToken(), inputData.saleId, inputData.amountBonuses);
    }
}
