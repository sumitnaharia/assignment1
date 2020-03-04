package com.example.myapplication.network

import com.example.myapplication.responsemodel.WeekDietData
import com.example.myapplication.responsemodel.WeekDietRootModel
import com.example.myapplication.utils.Constants
import retrofit2.Call
import retrofit2.http.*


interface WorkDietData {

        @GET(Constants.DUMMY)
        abstract  fun getDietPlan(): Call<WeekDietRootModel>

    }
