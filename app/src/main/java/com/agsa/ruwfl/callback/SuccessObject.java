package com.agsa.ruwfl.callback;

/**
 * Created by Evillatoro on 14/11/2016.
 */

public interface SuccessObject<T> {
    void onSuccess(T object);

    void onError(T object);
}
