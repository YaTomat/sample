package ais.sample.com.common;

import android.os.Bundle;

/**
 * Created by YaTomat on 12.06.2017.
 */

public abstract class BasePresenter<T> {

    private T view;
    private boolean isViewAvailable;

    public void onViewCreated(T view, Bundle savedInstance) {
        this.view = view;
    }

    public void onViewDestroyed() {
        this.view = null;
    }

    public void onStart() {
        isViewAvailable = true;
    }

    public void onStop() {
        isViewAvailable = false;
    }

    public T getView() {
        return view;
    }

    public boolean isViewAvailable() {
        return view != null && isViewAvailable;
    }
}
