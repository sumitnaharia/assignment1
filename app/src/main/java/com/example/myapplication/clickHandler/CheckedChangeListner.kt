package com.example.myapplication.clickHandler

import android.view.View

interface CheckedChangeListner<T> {
    fun onCheckedChange(item: T, view: View, checked: Boolean)
}