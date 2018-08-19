package ais.sample.com.common;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.TextView;

import ais.sample.com.R;


/**
 * Created by YaTomat on 19.08.2017.
 */

public class FullscreenProgressDialogFragment extends DialogFragment {

    private static final String ARG_MESSAGE_RES_ID = "ARG_MESSAGE_RES_ID";

    public static FullscreenProgressDialogFragment newInstance() {
        return new FullscreenProgressDialogFragment();
    }

    public static FullscreenProgressDialogFragment newInstance(@StringRes int messageResId) {
        Bundle args = new Bundle();
        args.putInt(ARG_MESSAGE_RES_ID, messageResId);
        FullscreenProgressDialogFragment fragment = new FullscreenProgressDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_fullscreen_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return dialog;
    }
}
