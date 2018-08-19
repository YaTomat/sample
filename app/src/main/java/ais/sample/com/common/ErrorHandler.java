package ais.sample.com.common;

import android.support.annotation.StringRes;

/**
 * Created by YaTomat on 12.06.2017.
 */

public interface ErrorHandler {

    void handleError();

    void handleError(@StringRes int errorMessage);

    void handleError(String errorMessage);
}
