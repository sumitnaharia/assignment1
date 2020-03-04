package com.example.myapplication.responsemodel

import androidx.room.Entity
import org.jetbrains.annotations.NotNull
import java.sql.RowId

@Entity(tableName = "meals", primaryKeys = arrayOf("id"))
class Meals {
    lateinit var food: String
    lateinit var meal_time: String
    var day: Int = 0
    var check: Boolean = false

    @NotNull
    var id: Int = 0


    fun getDayValue(): String {
        when (day) {
            1 ->
                return "Monday"
            2 ->
                return "tuesday"
            3 ->
                return "Wednesday"
            4 ->
                return "Thursday"
            5 ->
                return "Friday"
            6 ->
                return "Saturday"
            7 ->
                return "Sunday"
        }
        return ""
    }
}