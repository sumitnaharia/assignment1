package com.example.myapplication.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.BR
import com.example.myapplication.binder.CompositeItemBinder
import com.example.myapplication.binder.ConditionalDataBinder
import com.example.myapplication.binder.ItemBinder
import com.example.myapplication.clickHandler.CheckedChangeListner
import com.example.myapplication.clickHandler.ClickHandler
import com.example.myapplication.clickHandler.RecuclerChildClickHandler
import com.example.myapplication.network.*
import com.example.myapplication.repositry.MealsRepository
import com.example.myapplication.responsemodel.Meals
import java.util.*
import android.app.PendingIntent
import android.content.Intent
import android.app.AlarmManager
import android.content.Context
import com.example.myapplication.AssigmentApplication
import com.example.myapplication.reciver.DietReciver
import com.example.myapplication.database.AppDatabase
import kotlin.collections.ArrayList


class DietPlanViewModel(application: Application) : BaseViewModel(application) {
    lateinit var mealsRepository: MealsRepository
    lateinit var mealsClickLiveData: MutableLiveData<Meals>
    var database: AppDatabase


    init {
        mealsRepository = MealsRepository()
        mealsClickLiveData = MutableLiveData()
        database = AppDatabase.getDatabase(AssigmentApplication.getApplicationInstance())

    }

    fun loadData(): LiveData<Resource<List<Meals>>> {

        return mealsRepository.loadData()
    }

    /*Trending item view binder*/
    fun <T> itemViewBinder(): ItemBinder<T> {
        return CompositeItemBinder<T>(object :
            ConditionalDataBinder<T>(BR.meal, com.example.myapplication.R.layout.diet_item) {
            override fun canHandle(model: T): Boolean {
                return true
            }
        })
    }

    fun <T> clickHandler(): ClickHandler<T> {
        return object : ClickHandler<T> {
            override fun onClick(item: T, view: View) {
                mealsClickLiveData.value = (item as Meals)
            }
        }
    }

    fun <T> getOnCheckedListner(): CheckedChangeListner<T> {
        return object : CheckedChangeListner<T> {
            override fun onCheckedChange(item: T, view: View, checked: Boolean) {

            }

        }
    }

    inline fun getClander(meal: Meals): Calendar {
        var hourOfDay = meal.meal_time.split(":")[0].toInt()
        var minutesOfDay = meal.meal_time.split(":")[1].toInt()
        if (minutesOfDay < 5) {
            if (hourOfDay == 0) {
                hourOfDay = 23
            } else {
                hourOfDay -=1
            }
            minutesOfDay = 60 - (5-minutesOfDay)
        } else {
            minutesOfDay -= 5
        }
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, getDayOfMonth(meal.day))
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minutesOfDay)
        return calendar
    }


    fun getPendingIntent(meal: Meals): PendingIntent {
        val myIntent: Intent
        val pendingIntent: PendingIntent
        myIntent = Intent(AssigmentApplication.getApplicationInstance(), DietReciver::class.java)
        myIntent.putExtra("meal_name", meal.food)
        pendingIntent = PendingIntent.getBroadcast(
            AssigmentApplication.getApplicationInstance(), meal.id,
            myIntent,
            0
        )
        return pendingIntent
    }

    fun <T> chieldClickHandler(): RecuclerChildClickHandler<T> {
        return object : RecuclerChildClickHandler<T> {
            override fun onClick(viewModel: T, v: View, position: Int) {
                val pendingIntent: PendingIntent
                var meal = viewModel as Meals
                val manager =
                    v.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                pendingIntent = getPendingIntent(meal)
                var calendar = getClander(meal)
//                meal.check = !meal.check
                if (meal.check) {
                    manager!!.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        7 * 24 * 60 * 60 * 1000,
                        pendingIntent
                    )

                } else {
                    manager!!.cancel(pendingIntent)
                }
                var list = ArrayList<Meals>()
                list.add(meal)
                database.mealsDao().insertAllMeals(list)
            }
        }
    }

    fun getDayOfMonth(value: Int): Int {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        when (value) {
            1 -> {
                when (day) {
                    Calendar.SUNDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 1

                    }
                    Calendar.MONDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH)

                    }
                    Calendar.TUESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 6

                    }
                    Calendar.FRIDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 3
                    }
                    Calendar.THURSDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 4

                    }
                    Calendar.WEDNESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 5

                    }
                    Calendar.SATURDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 2
                    }

                }

            }
            2 -> {
                when (day) {
                    Calendar.SUNDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 2

                    }
                    Calendar.MONDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 1

                    }
                    Calendar.TUESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH)

                    }
                    Calendar.FRIDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 4
                    }
                    Calendar.THURSDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 5

                    }
                    Calendar.WEDNESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 6

                    }
                    Calendar.SATURDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 3
                    }

                }

            }
            3 -> {
                when (day) {
                    Calendar.SUNDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 3

                    }
                    Calendar.MONDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 2

                    }
                    Calendar.TUESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 1

                    }
                    Calendar.FRIDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 5
                    }
                    Calendar.THURSDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 6

                    }
                    Calendar.WEDNESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH)

                    }
                    Calendar.SATURDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 4
                    }

                }

            }
            4 -> {
                when (day) {
                    Calendar.SUNDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 4

                    }
                    Calendar.MONDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 3

                    }
                    Calendar.TUESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 2

                    }
                    Calendar.FRIDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 6
                    }
                    Calendar.THURSDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH)

                    }
                    Calendar.WEDNESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 1

                    }
                    Calendar.SATURDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 5
                    }

                }

            }
            5 -> {
                when (day) {
                    Calendar.SUNDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 5

                    }
                    Calendar.MONDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 4

                    }
                    Calendar.TUESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 3

                    }
                    Calendar.FRIDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH)
                    }
                    Calendar.THURSDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 1

                    }
                    Calendar.WEDNESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 2

                    }
                    Calendar.SATURDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 6
                    }

                }

            }
            6 -> {
                when (day) {
                    Calendar.SUNDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 6

                    }
                    Calendar.MONDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 5

                    }
                    Calendar.TUESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 4

                    }
                    Calendar.FRIDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 1
                    }
                    Calendar.THURSDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 2

                    }
                    Calendar.WEDNESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 3

                    }
                    Calendar.SATURDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH)
                    }

                }

            }
            7 -> {
                when (day) {
                    Calendar.SUNDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH)

                    }
                    Calendar.MONDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 6

                    }
                    Calendar.TUESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 5

                    }
                    Calendar.FRIDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 2
                    }
                    Calendar.THURSDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 3

                    }
                    Calendar.WEDNESDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 4

                    }
                    Calendar.SATURDAY -> {
                        return calendar.get(Calendar.DAY_OF_MONTH) + 1
                    }

                }

            }


        }
        return 0
    }

    fun getDayValue(value: Int) {

    }
}