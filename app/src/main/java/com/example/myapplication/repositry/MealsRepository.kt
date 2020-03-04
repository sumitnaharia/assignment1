package com.example.myapplication.repositry

import androidx.lifecycle.LiveData
import com.example.myapplication.AssigmentApplication
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.NetworkBoundResource
import com.example.myapplication.network.Resource
import com.example.myapplication.network.WorkDietData
import com.example.myapplication.responsemodel.Meals
import com.example.myapplication.responsemodel.WeekDietRootModel
import io.reactivex.Observable
import retrofit2.Call

class MealsRepository {
    lateinit var database: AppDatabase

    init {
        database = AppDatabase.getDatabase(AssigmentApplication.getApplicationInstance())
    }


    fun loadData(): LiveData<Resource<List<Meals>>> {

        return object : NetworkBoundResource<List<Meals>, WeekDietRootModel>() {
            protected override fun saveCallResult(item: WeekDietRootModel) {
                var i = 0


//                var list = Observable.just(item).concatMap {
//                    Observable.just(
//                        it.week_diet_data.monday,
//                        it.week_diet_data.wednesday,
//                        it.week_diet_data.thursday
//                    )
//                }.filter {
//                    if (it != null)
//                        true
//                    else
//                        false
//                }.concatMap {
//                    Observable.fromIterable(it)
//                }.map {
//                    it.id = i++
//                    it
//                }.toList().blockingGet()
                if (database.mealsDao().getMealsList().size <= 0)
                    database.mealsDao().insertAllMeals(makeList(item))
            }


            protected override fun loadFromDb(): LiveData<List<Meals>> {
                //MutableLiveData<List<Inbox>> responseLiveData = new MutableLiveData<>();
                //                responseLiveData.setValue(database.inboxDao().);
                return database.mealsDao().getMealsLiveList()
                //                return responseLiveData;
            }

            protected override fun createCall(): Call<WeekDietRootModel> {


                return ApiClient.client.create(WorkDietData::class.java)
                    .getDietPlan()
            }
        }.asLiveData
    }

    private fun makeList(item: WeekDietRootModel): ArrayList<Meals> {
        var listData = ArrayList<Meals>()

        item.week_diet_data.monday?.let {
            listData.addAll(converData(it, listData.size, 1))
        }
        item.week_diet_data.tueday?.let {
            listData.addAll(converData(it, listData.size, 2))

        }
        item.week_diet_data.wednesday?.let {
            listData.addAll(converData(it, listData.size, 3))

        }
        item.week_diet_data.thursday?.let {
            listData.addAll(converData(it, listData.size, 4))

        }
        item.week_diet_data.friday?.let {
            listData.addAll(converData(it, listData.size, 5))

        }
        item.week_diet_data.saturday?.let {
            listData.addAll(converData(it, listData.size, 6))

        }
        item.week_diet_data.sunday?.let {
            listData.addAll(converData(it, listData.size, 7))

        }
        return listData
    }

    fun converData(it: ArrayList<Meals>, id: Int, day: Int): ArrayList<Meals> {
        var dayId = id
        var listData = ArrayList<Meals>()
        listData = Observable.fromIterable(it).map {
            it.id = ++dayId
            it.day = day
            it

        }.toList().blockingGet() as ArrayList<Meals>
        return listData
    }
}