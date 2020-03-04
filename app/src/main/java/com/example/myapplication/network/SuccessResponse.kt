package com.example.myapplication.network
     interface SuccessResponse<T> {
         fun onSuccess(response: T, requestType: String)
     }

