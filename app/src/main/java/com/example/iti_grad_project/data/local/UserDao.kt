package com.example.iti_grad_project.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: User)

//    suspend fun loginUser(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username= :username")
    suspend fun getUser(username: String): User?
}