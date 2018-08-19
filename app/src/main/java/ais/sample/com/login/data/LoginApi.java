package ais.sample.com.login.data;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YaTomat on 10.06.2017.
 */

public interface LoginApi {

    @GET("api/auth")
    Observable<ResponseBody> getToken(@Query("UserLogin") String userLogin,
                                      @Query("UserPassword") String userPassword,
                                      @Query("TerminalLogin") String terminalLogin,
                                      @Query("TerminalPassword") String terminalPassword);

}
