package com.example.new_task.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.new_task.entity.User


@Dao
interface UserDuos {

    @Insert
    fun insertData(user: User)


    @Query("Select * From User")
    fun getData(): List<User>


    @Query("SELECT * FROM User WHERE email LIKE :Email")
    fun findByData(Email: String): User?


    @Query("UPDATE User SET pass = :Pass WHERE email = :Email")
    fun updateUser(Email: String?, Pass: String?): Int






}