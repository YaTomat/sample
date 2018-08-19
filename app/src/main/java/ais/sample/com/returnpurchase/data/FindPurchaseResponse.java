package ais.sample.com.returnpurchase.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import ais.sample.com.statistic.data.StatisticItem;

/**
 * Created by YaTomat on 12.09.2017.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FindPurchaseResponse {
    public List<StatisticItem> list;

    public FindPurchaseResponse(List<StatisticItem> list) {
        this.list = list;
    }
}
