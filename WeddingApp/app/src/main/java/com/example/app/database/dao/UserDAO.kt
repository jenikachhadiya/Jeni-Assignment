package com.example.app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app.database.entity.User

@Dao
interface UserDAO {

    @Insert
    fun insertRecord(user: User)


    @Query("select * from 'user_table'")
    fun getAllRecords(): List<User>

    @Query("SELECT * from 'user_table' where email=:email")
    fun selectUser(email: String?): User?


}