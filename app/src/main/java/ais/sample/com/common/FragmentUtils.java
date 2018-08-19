package ais.sample.com.common;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by YaTomat on 29.08.2017.
 */

public class FragmentUtils {

    public static void show(FragmentManager fragmentManager, DialogFragment dialogFragment) {
        dialogFragment.setCancelable(false);
        dialogFragment.show(fragmentManager, FullscreenProgressDialogFragment.class.getName());
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }
}
