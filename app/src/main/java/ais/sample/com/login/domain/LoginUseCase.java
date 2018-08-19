package ais.sample.com.login.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ais.sample.com.common.BaseUseCase;
import ais.sample.com.login.data.LoginApi;
import ais.sample.com.login.data.LoginRequest;
import ais.sample.com.login.data.LoginResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by YaTomat on 12.06.2017.
 */

public class LoginUseCase extends BaseUseCase<LoginRequest, LoginResponse> {

    private final LoginApi loginApi;

    public LoginUseCase(Scheduler uiScheduler, Scheduler workerScheduler, LoginApi loginApi) {
        super(uiScheduler, workerScheduler);
        this.loginApi = loginApi;
    }

    @Override
    public Observable<LoginResponse> buildObservable(LoginRequest inputData) {
        return loginApi
                .getToken(inputData.sellerLogin,
                        inputData.sellerPassword,
                        inputData.terminalLogin,
                        inputData.terminalPassword)
                .flatMap((Function<ResponseBody, ObservableSource<LoginResponse>>) responseBody -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    LoginResponse item = mapper.readValue
                            (responseBody.string(), LoginResponse.class);
                    return Observable.just(item);
                });
    }
}
