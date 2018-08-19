package ais.sample.com.returnpurchase.presentation;

import ais.sample.com.common.ErrorHandler;
import ais.sample.com.common.ProgressController;
import ais.sample.com.common.StatelessView;
import ais.sample.com.returnpurchase.data.FindPurchaseResponse;
import io.reactivex.Observable;

/**
 * Created by YaTomat on 12.09.2017.
 */

public interface FindPurchaseView extends ErrorHandler, ProgressController, StatelessView {

    Observable<String> getPurchaseDate();

    Observable<String> getCardNumber();

    Observable<String> getPhoneNumber();

    Observable<String> getAmountPurchase();

    Observable<Object> onFindClick();

    void updateView(FindPurchaseResponse findPurchaseResponse);
}
