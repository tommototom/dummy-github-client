package com.github.app.networking;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import com.github.app.App;
import com.github.app.UI.BaseActivity;
import com.github.app.db.RealmDao;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class RetrofitLoaderManager {

    public static <E, R> void init(final LoaderManager manager, final int loaderId,
            final RetrofitLoader<E, R> loader, final LoaderCallback<E> callback, final RealmDao dao) {
        manager.initLoader(loaderId, Bundle.EMPTY, new LoaderCallbacks<Response<E>>() {

            @Override
            public Loader<Response<E>> onCreateLoader(int id, Bundle args) {
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Response<E>> l, Response<E> data) {
                if (data.hasError()) {
                    callback.onLoadFailure(data.getException());
                } else {
                    E result = data.getResult();

                    if (isLoaderPageable(loader)) {
                        RetrofitLoader rl = (RetrofitLoader) loader;
                        dao.deletePage(rl.getEntityClass(), rl.getPageNumber());
                        dao.saveAsPageable(result, rl.getPageNumber());
                    } else {
                        dao.delete(result);
                        dao.save(result);
                    }

                    callback.onLoadSuccess(result);
                }
            }

            @Override
            public void onLoaderReset(Loader<Response<E>> loader) {
                //Nothing to do here
            }
        });
    }


    private static <E> boolean isLoaderPageable(Loader<Response<E>> loader) {
        return loader instanceof RetrofitLoader && ((RetrofitLoader) loader).getPageNumber() != null;
    }

}