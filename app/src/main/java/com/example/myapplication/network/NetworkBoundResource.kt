package com.example.myapplication.network

import android.os.AsyncTask
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.myapplication.AssigmentApplication
import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    val asLiveData: LiveData<Resource<ResultType>>
        get() = result

    init {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        createCall().enqueue(object : Callback<RequestType> {
            override fun onResponse(call: Call<RequestType>, response: Response<RequestType>) {
                result.removeSource(dbSource)
                saveResultAndReInit(response.body())
            }

            override fun onFailure(call: Call<RequestType>, throwable: Throwable) {
                onFetchFailed()
                result.removeSource(dbSource)
                result.addSource(dbSource) { newData ->
                    result.setValue(
                        if (throwable is IOException)
                            Resource.errorNetworkWithOldData(
                                AssigmentApplication.getApplicationInstance().getString(
                                    R.string.error_network
                                ), dbSource.value
                            )
                        else
                            Resource.errorWithOldData(throwable.message!!, dbSource.value)
                    )
                }
            }
        })
    }

    @MainThread
    private fun saveResultAndReInit(response: RequestType) {
        object : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg voids: Void): String {
                saveCallResult(response)
                return ""
            }

            override fun onPostExecute(aVoid: String) {
                result.addSource(loadFromDb()) { newData ->
                    result.setValue(
                        Resource.success(
                            newData
                        )
                    )
                }
            }
        }.execute()
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected fun shouldFetch(data: ResultType?): Boolean {
        return true
    }

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>
    /*

@NonNull
@MainThread
protected abstract LiveData<ResultType> loadFromDb(LiveData<ResultType> source);
*/

    @MainThread
    protected abstract fun createCall(): Call<RequestType>

    /*
    @NonNull
    @MainThread
    protected abstract Disposable createNetworkCall();*/
    @MainThread
    protected fun onFetchFailed() {
    }
}

