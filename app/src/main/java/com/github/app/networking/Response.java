package com.github.app.networking;

class Response<E> {

    private Exception mException;
    private E mResult;


    static <E> Response<E> ok(E data){
        Response<E> response = new Response<E>();
        response.mResult = data;

        return  response;
    }

    static <E> Response<E> error(Exception ex){
        Response<E> response = new Response<E>();
        response.mException = ex;

        return  response;
    }

    public boolean hasError() {
        return mException != null;
    }

    public Exception getException() {
        return mException;
    }

    public E getResult() {
        return mResult;
    }
}