package com.example.new_task.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.new_task.Daos.UserDuos
import com.example.new_task.entity.User


@Database(entities = [User::class], version = 1)
abstract class Userdatabase : RoomDatabase() {

    abstract fun userdaos(): UserDuos
}