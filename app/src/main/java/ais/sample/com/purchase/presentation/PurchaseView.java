package ais.sample.com.purchase.presentation;

import java.util.List;

import ais.sample.com.common.ErrorHandler;
import ais.sample.com.common.ProgressController;
import ais.sample.com.common.StatelessView;
import ais.sample.com.purchase.data.TransactionResponse;
import io.reactivex.Observable;

/**
 * Created by YaTomat on 01.07.2017.
 */

public interface PurchaseView extends ErrorHandler,ProgressController, StatelessView {
    Observable<String> onPurchaseAmountChanged();

    Observable<String> onCardNumberChanged();

    Observable<String> onPhoneNumberChanged();

    Observable<Integer> onStockChanged();

    Observable<Object> onPerformTransactionPress();

    void updateStocks(List<String> stocks);

    void updateCardNumber(String code);

    void onTransactionFinished(TransactionResponse transactionResponse);
}
