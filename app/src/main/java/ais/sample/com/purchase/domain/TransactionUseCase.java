package ais.sample.com.purchase.domain;

import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseUseCase;
import ais.sample.com.purchase.data.TransactionApi;
import ais.sample.com.purchase.data.TransactionRequest;
import ais.sample.com.purchase.data.TransactionResponse;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by YaTomat on 01.07.2017.
 */

public class TransactionUseCase extends BaseUseCase<TransactionRequest, TransactionResponse> {

    private final TransactionApi transactionApi;
    private final AccessController accessController;

    public TransactionUseCase(Scheduler uiScheduler, Scheduler workerScheduler, TransactionApi transactionApi,
                              AccessController accessController) {
        super(uiScheduler, workerScheduler);
        this.accessController = accessController;
        this.transactionApi = transactionApi;
    }

    @Override
    protected Observable<TransactionResponse> buildObservable(TransactionRequest inputData) {
        return transactionApi.makePurchase(accessController.getAccessToken(), inputData.cardNumber == null ? "null" : inputData.cardNumber,
                inputData.phoneNumber == null ? "null" : inputData.phoneNumber, inputData.summ, inputData.actionId);
    }
}
