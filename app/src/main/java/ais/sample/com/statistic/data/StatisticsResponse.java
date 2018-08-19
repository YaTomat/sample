package ais.sample.com.statistic.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by YaTomat on 30.08.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsResponse {
    @JsonProperty(value = "Sales")
    public List<StatisticItem> statisticItems;
    @JsonProperty(value = "TotalAmtPayedWithBonuses")
    public float totalBonusesPaid;

}
