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

    @Query("UPDATE users SET profileImagePath = :imagePath WHERE username = :username")
    suspend fun updateUserImage(username: String, imagePath: String)

    @Query("SELECT profileImagePath FROM users WHERE username = :username")
    suspend fun getProfileImage(username: String): String?
}