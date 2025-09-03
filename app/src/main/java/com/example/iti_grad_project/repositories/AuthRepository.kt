package com.example.iti_grad_project.repositories

import com.example.iti_grad_project.data.local.User
import com.example.iti_grad_project.data.local.UserDao
import com.example.iti_grad_project.data.prefs.PreferenceManager
import okhttp3.internal.connection.Exchange

class AuthRepository(
    private val userDao: UserDao,
    private val prefs: PreferenceManager
) {
    suspend fun register(username: String, email: String, password: String): String {
        return try {
            if(userDao.getUser(username) != null)
                return "User already existed"

            val user = User(username, email, password)
            userDao.registerUser(user)
            prefs.setLoggedIn(true)
            prefs.setUsername(username)

            "Registration Done Successfully"
        } catch (e: Exception) {
            "Error occurred"
        }
    }


    suspend fun login(username: String, password: String): String {
        return try {
            val user = userDao.getUser(username)
            if(user == null)
                return "Error in Credentials"

            if(user.password != password)
                return "Error in Credentials"

            prefs.setUsername(username)
            prefs.setLoggedIn(true)

            "Login Done successfully"
        } catch (e: Exception) {
            "Error occurred"
        }
    }

    suspend fun getProfile(): User? {
        if(!prefs.isLoggedIn())
            return null

        return userDao.getUser(prefs.getUsername()!!)
    }

    fun logout() {
        prefs.setLoggedIn(false)
        prefs.clearUsername()
    }

    fun isLoggedIn(): Boolean = prefs.isLoggedIn()

    fun getUserName(): String? = prefs.getUsername()
}