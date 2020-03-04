package com.example.myapplication.reciver

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import com.example.DietIntentService


class DietReciver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var value = intent.getStringExtra("meal_name")
        DietIntentService.enqueueWork(context, value);
//        val intent1 = Intent(context, DietIntentService::class.java)
//        intent1.putExtra("meal_name", value)
//        context.startService(intent1)
    }
}
