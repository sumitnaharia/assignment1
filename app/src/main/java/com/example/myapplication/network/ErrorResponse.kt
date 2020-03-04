package com.example.myapplication.network


     interface ErrorResponse<T> {
         fun onError(error: T, requestType: String)
     }
