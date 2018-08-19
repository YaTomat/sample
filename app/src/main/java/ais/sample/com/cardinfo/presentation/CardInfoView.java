package ais.sample.com.cardinfo.presentation;

import ais.sample.com.cardinfo.data.CardInfoResponse;
import ais.sample.com.common.ErrorHandler;
import ais.sample.com.common.ProgressController;
import ais.sample.com.common.StatelessView;
import io.reactivex.Observable;

/**
 * Created by YaTomat on 10.09.2017.
 */

public interface CardInfoView extends ErrorHandler, StatelessView, ProgressController {
    void updateView(CardInfoResponse cardInfoResponse);

    Observable<String> onCardNumberChanged();

    Observable<Object> onGetInfoClick();
}
