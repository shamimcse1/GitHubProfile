package com.example.githubprofile.helpers;

public class DataResources<T> {

    private DataStatus status;
    private T data;
    private String message;

    public DataResources(DataStatus status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> DataResources<T> loading() {
        return new DataResources(DataStatus.LOADING, null, null);
    }

    public static <T> DataResources<T> success(T data) {
        return new DataResources(DataStatus.SUCCESS, data, null);
    }

    public static <T> DataResources<T> error(String message) {
        return new DataResources(DataStatus.ERROR, null, message);
    }

    public T getActualData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public DataStatus getStatus() {
        return status;
    }
}
