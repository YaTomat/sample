package ais.sample.com.common;

import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import ais.sample.com.R;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

/**
 * Created by YaTomat on 29.08.2017.
 */

public abstract class ErrorHandlingObserver<T> implements Observer<T> {
    private final ErrorHandler errorHandler;

    public ErrorHandlingObserver(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public boolean handleError(@NonNull Throwable error) {
        Log.e("ERROR HAPPENS", "Something bad", error);
        if (error instanceof IOException) {
            if (errorHandler != null) {
                if (error instanceof SocketTimeoutException) {
                    errorHandler.handleError(R.string.no_server_respond);
                    return true;
                }
                if (error instanceof UnknownHostException) {
                    errorHandler.handleError(R.string.no_network_connection);
                    return true;
                }
                errorHandler.handleError(R.string.unknown_error);
                return true;
            }
        }
        return false;
    }
}
