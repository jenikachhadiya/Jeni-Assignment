package com.example.new_task.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.new_task.daos.UserDuos
import com.example.new_task.entity.User


@Database(entities = [User::class], version = 2)
abstract class Userdatabase : RoomDatabase() {

    abstract fun userdaos(): UserDuos
}