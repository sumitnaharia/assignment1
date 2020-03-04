package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.responsemodel.Meals


@Dao
interface MealsDao {
    @Query("select * from meals")
    fun getMealsLiveList(): LiveData<List<Meals>>

    @Query("select * from meals")
    fun getMealsList(): List<Meals>

//        @Query("select * from restorent")
//        fun getPersonalBookingDataFactory(): DataSource.Factory<Int, Restorent>

    @Query("select * from meals where id=:arg0")
    fun getMealsListById(arg0: String): Meals

    @Query("select * from meals where id=:arg0")
    fun getMealsFromId(arg0: String): LiveData<Meals>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMeals(persnalBooking: List<Meals>)


    @Query("DELETE FROM meals")
    abstract fun deleteAllMeals()
}



