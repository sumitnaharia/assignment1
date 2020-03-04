package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.AssigmentApplication
import com.example.myapplication.R
import com.example.myapplication.adapter.RecyclerViewAdapter
import com.example.myapplication.databinding.ActivityDietPlanBinding
import com.example.myapplication.responsemodel.Meals
import com.example.myapplication.viewmodel.DietPlanViewModel
import com.example.myapplication.utils.Constants.Companion.Status.Companion.ERROR
import com.example.myapplication.utils.Constants.Companion.Status.Companion.LOADING
import com.example.myapplication.utils.Constants.Companion.Status.Companion.SUCCESS

class DietPlanActivity :
    BaseActivity<DietPlanViewModel, ActivityDietPlanBinding>() {

    override fun getLayoutRes(): Int {

        return R.layout.activity_diet_plan
    }

    override fun getViewModel(): Class<DietPlanViewModel> {
        return DietPlanViewModel::class.java
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.layoutManager = LinearLayoutManager(this)
        binding.viewModel = viewModel

        setupActionbar()
        setObserVer()
    }

    private fun setObserVer() {
        viewModel.mealsClickLiveData.observe(this, Observer {
            it
        })
        viewModel.loadData().observe(this, androidx.lifecycle.Observer {
            if (it.status == SUCCESS) {
                it.data?.let { it1 ->
                    (binding.rvSongs.adapter as RecyclerViewAdapter<Meals>).items!!.clear()
                    (binding.rvSongs.adapter as RecyclerViewAdapter<Meals>).items!!.addAll(
                        it1
                    )
                    (binding.rvSongs.adapter as RecyclerViewAdapter<Meals>).notifyDataSetChanged()
                }
                showProgress(false)

            } else if (it.status == LOADING) {
                showProgress(true)
            } else if (it.status == ERROR) {

                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun setupActionbar() {
        super.setupActionbar()
        val toolbar = binding.includeToolbar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        binding.includeToolbar.titleToolbar.text = "Diet Chart"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
