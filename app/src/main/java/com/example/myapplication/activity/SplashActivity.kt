package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.AssigmentApplication
import com.example.myapplication.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    init {
        compositeDisposable!!.add(
            Observable.timer(3, TimeUnit.SECONDS)
                .subscribe {
                    openMainActivity()
                })

    }

    private fun openMainActivity() {
        startActivity(Intent(AssigmentApplication.getApplicationInstance(), DietPlanActivity::class.java))
        finish()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable!!.dispose()

    }
}
