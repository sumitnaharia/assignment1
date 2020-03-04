package com.example.myapplication.utils

import android.text.Editable
import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

public class Constants {
    companion object {
        public val MAPS_DESTINATION: Int
            get() = 100

        public fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
        public const val DUMMY: String = "dummy/"
        public const val BASE_URL: String = "  http://3.14.116.40:8000/"

        @IntDef(
            Status.SUCCESS,
            Status.ERROR,
            Status.LOADING,
            Status.ERROR_NETWORK,
            Status.ERROR_NETWORK_WITH_DATA,
            Status.ERROR_GENERAL,
            Status.ERROR_GENERAL_WITH_DATA,
            Status.ERROR_LANDING,
            Status.ERROR_LANDING_WITH_DATA,
            Status.ERROR_LOADING_WITH_DATA
        )
        @Retention(RetentionPolicy.SOURCE)
        annotation class Status {
            companion object {
                const val SUCCESS = 0
                const val LOADING = 1
                const val ERROR_NETWORK = 2
                const val ERROR = 3
                const val ERROR_LANDING = 4
                const val ERROR_NETWORK_WITH_DATA = 5
                const val ERROR_GENERAL = 9
                const val ERROR_GENERAL_WITH_DATA = 6
                const val ERROR_LANDING_WITH_DATA = 7
                const val ERROR_LOADING_WITH_DATA = 8
            }
        }
    }
}