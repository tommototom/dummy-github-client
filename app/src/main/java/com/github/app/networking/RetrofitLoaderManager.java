package com.github.app.networking;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;

public class RetrofitLoaderManager {

    public static <E, R> void init(final LoaderManager manager, final int loaderId,
            final RetrofitLoader<E, R> loader, final LoaderCallback<E> callback) {
        manager.initLoader(loaderId, Bundle.EMPTY, new LoaderCallbacks<Response<E>>() {

            @Override
            public Loader<Response<E>> onCreateLoader(int id, Bundle args) {
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Response<E>> loader, Response<E> data) {
                if (data.hasError()) {
                    callback.onLoadFailure(data.getException());
                } else {
                    callback.onLoadSuccess(data.getResult());
                }
            }

            @Override
            public void onLoaderReset(Loader<Response<E>> loader) {
                //Nothing to do here
            }
        });
    }
}