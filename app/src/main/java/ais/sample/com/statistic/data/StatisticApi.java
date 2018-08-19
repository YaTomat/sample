package ais.sample.com.statistic.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YaTomat on 30.08.2017.
 */

public interface StatisticApi {

    @GET("api/sale")
    Observable<StatisticsResponse> getStatistic(@Query("AccessToken") String accessToken,
                                                @Query("StartDate") String startDate,
                                                @Query("EndDate") String endDate,
                                                @Query("ForUser") boolean forUser);
}
