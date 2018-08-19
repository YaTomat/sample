package ais.sample.com.statistic.data;

/**
 * Created by YaTomat on 30.08.2017.
 */

public class StatisticRequest {

    public String endDate;
    public String startDate;
    public Boolean isTerminal;

    public StatisticRequest(String endDate, String startDate, Boolean isTerminal) {
        this.endDate = endDate;
        this.startDate = startDate;
        this.isTerminal = isTerminal;
    }
}
