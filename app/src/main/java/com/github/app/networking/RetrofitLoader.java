package com.github.app.networking;

import android.content.AsyncTaskLoader;
import android.content.Context;

public abstract class RetrofitLoader<E, R> extends AsyncTaskLoader<Response<E>> {

    private final R mService;

    private Response<E> mCachedResponse;

    public RetrofitLoader(Context context, R service) {
        super(context);
        mService = service;
    }

    @Override
    public Response<E> loadInBackground() {
        try {
            final E data = call(mService);
            mCachedResponse = Response.ok(data);
        } catch (Exception ex) {
            mCachedResponse = Response.error(ex);
        }

        return mCachedResponse;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (mCachedResponse != null) {
            deliverResult(mCachedResponse);
        }

        if (takeContentChanged() || mCachedResponse == null) {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        mCachedResponse = null;
    }

    public abstract E call(R service);

    public abstract Class getEntityClass();

    /**
     * returns page number if data divided by pages or null otherwise
     */
    public abstract Integer getPageNumber();
}