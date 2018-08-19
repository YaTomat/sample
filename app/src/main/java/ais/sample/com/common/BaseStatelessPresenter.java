package ais.sample.com.common;

import android.os.Bundle;

/**
 * Created by YaTomat on 05.09.2017.
 */

public abstract class BaseStatelessPresenter<T> {
    private T view;
    private boolean isViewAvailable;
    private boolean isRestoreNeeded;

    public void onCreated(T view, Bundle savedInstance) {
        this.view = view;
        isRestoreNeeded = false;
    }

    public void onDestroyed() {
        this.view = null;
    }

    public void onStart() {
        isViewAvailable = true;
    }

    public void onStop() {
        isViewAvailable = false;
    }

    public void onSavedInstance(){
        isRestoreNeeded = true;
    }

    public boolean isRestoreNeeded(){
        return isRestoreNeeded;
    }

    public T getView() {
        return view;
    }

    public boolean isViewAvailable() {
        return view != null && isViewAvailable;
    }
}
