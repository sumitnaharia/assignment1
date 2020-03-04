package com.example.myapplication.network

import com.example.myapplication.utils.Constants.Companion.Status.Companion.ERROR
import com.example.myapplication.utils.Constants.Companion.Status.Companion.ERROR_GENERAL_WITH_DATA
import com.example.myapplication.utils.Constants.Companion.Status.Companion.ERROR_NETWORK_WITH_DATA
import com.example.myapplication.utils.Constants.Companion.Status.Companion.LOADING
import com.example.myapplication.utils.Constants.Companion.Status.Companion.SUCCESS


class Resource<T> private constructor(
    val status: Int, val data: T?, val message: String?
) {
    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> errorWithOldData(msg: String, data: T?): Resource<T> {
            return Resource(ERROR_GENERAL_WITH_DATA, data, msg)
        }

        fun <T> errorNetworkWithOldData(msg: String, data: T?): Resource<T> {
            return Resource(ERROR_NETWORK_WITH_DATA, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}

