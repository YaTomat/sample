package ais.sample.com.purchase.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import ais.sample.com.common.BaseUseCase;
import ais.sample.com.common.Constants;
import ais.sample.com.login.data.Action;
import ais.sample.com.login.data.LoginResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by YaTomat on 01.07.2017.
 */

public class GetStocksUseCase extends BaseUseCase<Object, List<Action>> {

    final Context context;

    public GetStocksUseCase(Scheduler uiScheduler, Scheduler workerScheduler, Context context) {
        super(uiScheduler, workerScheduler);
        this.context = context;
    }

    @Override
    protected Observable<List<Action>> buildObservable(Object inputData) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_ACTIONS, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            return Observable.just(sharedPreferences
                    .getString(Constants.PREFERENCES_ACTIONS_KEY, ""))
                    .flatMap((Function<String, ObservableSource<List<Action>>>) storageValue -> {
                        ObjectMapper mapper = new ObjectMapper();
                        List<Action> item = mapper.readValue(storageValue, new TypeReference<List<Action>>(){});
                        return Observable.just(item);
                    });
        } else {
            throw new IllegalArgumentException("No actions in shared preferences");
        }
    }
}
