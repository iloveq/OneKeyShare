package com.woaigmz.share.utils;

import android.os.AsyncTask;

/**
 * Created by haoran on 2018/8/3.
 */

public abstract class AbstractAsyncTask<T> extends AsyncTask<Void, Integer, T> {
    private Exception exception;
    private T data;

    @Override
    protected T doInBackground(Void... voids) {
        try {
            data = doLoadData();
            exception = null;
        } catch (Exception e) {
            data = null;
            exception = e;
        }
        return getData();
    }

    protected abstract T doLoadData() throws Exception;

    public Exception getException() {
        return exception;
    }

    public T getData() {
        return data;
    }

    @Override
    protected void onPostExecute(T t) {
        try {
            if (exception == null) {
                onSuccess(t);
            } else {
                onException(exception);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            onFinally();
        }

    }

    public void onSuccess(T t) {
    }

    public void onFinally() {
    }

    public void onException(Exception exception) {

    }
}
