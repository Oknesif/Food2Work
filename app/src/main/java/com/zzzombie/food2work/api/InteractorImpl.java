package com.zzzombie.food2work.api;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class InteractorImpl<T> implements Interactor<T> {

    private AsyncTask loadingTask;
    private Callback<T> listener;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadData(Supplier<ApiResponse<T>> apiSupplier) {
        if (loadingTask != null) {
            loadingTask.cancel(true);
        }
        loadingTask = new AsyncTask<Object, Void, ApiResponse<T>>() {

            @Override
            protected ApiResponse<T> doInBackground(Object... objects) {
                return apiSupplier.get();
            }

            @Override
            protected void onPostExecute(ApiResponse<T> response) {
                if (listener != null) {
                    listener.onResponse(response);
                }
            }
        };
        loadingTask.execute();
    }

    @Override
    public void cancelIfLoading() {
        if (loadingTask != null) {
            loadingTask.cancel(true);
            loadingTask = null;
        }
    }

    @Override
    public void setListener(Callback<T> callback) {
        this.listener = callback;
    }
}
