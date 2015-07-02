package com.github.app.networking;

public interface LoaderCallback<E> {
    void onLoadFailure(Exception ex);

    void onLoadSuccess(E result);
}