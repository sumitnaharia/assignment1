package com.example.myapplication.clickHandler

import android.view.View

interface ClickHandler<T> {
    fun onClick(item: T, view: View)
}
