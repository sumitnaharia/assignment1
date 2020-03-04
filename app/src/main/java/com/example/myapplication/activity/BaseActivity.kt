package com.example.myapplication.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.viewmodel.BaseViewModel


open abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var viewModel: VM
    protected lateinit var binding: DB
    private lateinit var mProgressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(getViewModel())
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle("Please wait")
        mProgressDialog.setCancelable(false)
        mProgressDialog.setMessage("Loading...")
        setupActionbar()
    }
    fun showProgress(show: Boolean) {
        try {
            if (show)
                mProgressDialog.show()
            else
                mProgressDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    open protected fun setupActionbar() {
    }

    protected abstract fun getViewModel(): Class<VM>
    protected abstract fun getLayoutRes(): Int
}
