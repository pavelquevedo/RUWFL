package com.agsa.ruwfl.callback;

import java.util.List;

/**
 * Created by Evillatoro on 14/11/2016.
 */

public interface SuccessArray<T> {
    void onSuccess(List<T> objectList);

    void onError(T object);
}
