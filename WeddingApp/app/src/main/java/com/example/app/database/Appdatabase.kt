package com.example.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app.database.dao.UserDAO
import com.example.app.database.entity.User

@Database(entities = [User::class],version = 1 )

 abstract class Appdatabase: RoomDatabase() {

    abstract val userDao: UserDAO

    companion object {
        private var INSTANCE: Appdatabase? = null
        fun getDatabase(context: Context): Appdatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, Appdatabase::class.java, "app_database").allowMainThreadQueries().build()

                }
            }
            return INSTANCE!!
        }
    }
}
