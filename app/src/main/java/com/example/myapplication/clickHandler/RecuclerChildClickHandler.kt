package com.example.myapplication.clickHandler
import android.view.View
interface RecuclerChildClickHandler<T> {
    fun onClick(viewModel: T, v: View, position: Int)
}
