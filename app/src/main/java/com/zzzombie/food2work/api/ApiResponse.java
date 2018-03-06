package com.zzzombie.food2work.api;

public class ApiResponse<T> {
    public final boolean successful;
    public final T data;
    public final Exception exception;

    public ApiResponse(Exception ex) {
        this.successful = false;
        this.exception = ex;
        this.data = null;
    }

    public ApiResponse(T data) {
        this.successful = true;
        this.exception = null;
        this.data = data;
    }
}
