package ais.sample.com.purchase.presentation;

import ais.sample.com.common.ErrorHandler;
import ais.sample.com.common.ProgressController;
import ais.sample.com.common.StatelessView;
import ais.sample.com.purchase.data.DiscountApplyResponse;
import io.reactivex.Observable;

/**
 * Created by YaTomat on 29.08.2017.
 */

interface DiscountPurchaseView extends ErrorHandler, ProgressController, StatelessView {

    Observable<String> onWritingOffBonusesChanged();

    Observable<Object> onPayBonusesClick();

    void finishWritingOffDiscount(DiscountApplyResponse discountApplyResponse);

    void showMaxDiscountAlert(String writingOffBonuses);

    void setWritingOffDiscountAmount(String value);
}
