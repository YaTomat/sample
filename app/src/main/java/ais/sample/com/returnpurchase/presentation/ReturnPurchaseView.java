package ais.sample.com.returnpurchase.presentation;

import java.util.ArrayList;

import ais.sample.com.common.ErrorHandler;
import ais.sample.com.common.ProgressController;
import ais.sample.com.common.StatelessView;
import ais.sample.com.statistic.data.StatisticItem;

/**
 * Created by YaTomat on 13.09.2017.
 */

public interface ReturnPurchaseView extends ErrorHandler, ProgressController, StatelessView {
    void updateView(ArrayList<StatisticItem> returnPurchaseResponse);
}
