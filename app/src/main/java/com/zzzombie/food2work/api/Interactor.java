package com.zzzombie.food2work.api;

public interface Interactor<T> {

    void loadData(Supplier<ApiResponse<T>> apiSupplier);

    void cancelIfLoading();

    void setListener(Callback<T> callback);

    interface Callback<T> {
        void onResponse(ApiResponse<T> response);
    }
}


