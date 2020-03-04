package com.example.myapplication.network

import io.reactivex.functions.Consumer


class NetworkSource<T>(
    private val requestType: String,
    successCallback: SuccessResponse<T>,
    errorCallback: ErrorResponse<Throwable>
) :
    Consumer<T> {
    private lateinit var errorCallback: ErrorResponse<Throwable>
    private lateinit var successCallback: SuccessResponse<T>

    init {
        this.successCallback = successCallback
        this.errorCallback = errorCallback
    }

    /*   public NetworkService(final RequestType requestType, final NetworkServiceCall<T1, T2, RequestType> serviceCallback) {
    this.requestType = requestType;
    this.serviceCallback = serviceCallback;
    new Consumer<T2>() {

        @Override
        public void accept(T2 t2) throws Exception {
            serviceCallback.onError(t2, requestType);
        }
    };
}*/

    @Throws(Exception::class)
    override fun accept(t: T) {
        if (t is Throwable) {
            errorCallback.onError(t, requestType)
        } else {
            successCallback.onSuccess(t, requestType)
        }
        //serviceCallback.onSuccess(t, requestType);
    }
}

