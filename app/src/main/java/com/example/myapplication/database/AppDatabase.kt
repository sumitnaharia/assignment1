package com.example.myapplication.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.dao.MealsDao
import com.example.myapplication.responsemodel.Meals


@Database(
    entities = [Meals::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealsDao(): MealsDao

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    companion object {
        private var INSTANCE: AppDatabase? = null
        private var mIsDatabaseCreated = MutableLiveData<Boolean>()

        @VisibleForTesting
        val DATABASE_NAME = "songs_database"
        private var executors: AppExecutors? = AppExecutors()
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(RoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        //                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "birdres_database").allowMainThreadQueries().build();
                        //                                .build();*/
                        executors = AppExecutors()
                        INSTANCE = buildDatabase(context.applicationContext, executors!!)
                        INSTANCE!!.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(
            appContext: Context,
            executors: AppExecutors
        ): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        setDatabaseCreated()

                    }
                }).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

        private fun setDatabaseCreated() {
            mIsDatabaseCreated.postValue(true)
        }
    }
}
