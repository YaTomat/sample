package ais.sample.com.common;

import android.support.annotation.StringRes;

/**
 * Created by YaTomat on 12.06.2017.
 */

public interface ProgressController {

    void showProgress();

    void showProgress(@StringRes int message);

    void stopProgress();
}
