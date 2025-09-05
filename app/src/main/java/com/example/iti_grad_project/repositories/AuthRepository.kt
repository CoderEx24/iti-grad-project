package com.example.iti_grad_project.repositories

import com.example.iti_grad_project.data.local.User
import com.example.iti_grad_project.data.local.UserDao
import com.example.iti_grad_project.data.prefs.PreferenceManager

class AuthRepository(
    private val userDao: UserDao,
    private val prefs: PreferenceManager
) {
    suspend fun register(username: String, email: String, password: String): Boolean {
        return try {
            if(userDao.getUser(username) != null)
                return false

            val user = User(username, email, password)
            userDao.registerUser(user)
            prefs.setLoggedIn(true)
            prefs.setUsername(username)

            true
        } catch (e: Exception) {
            false
        }
    }


    suspend fun login(username: String, password: String): Boolean {
        return try {
            val user = userDao.getUser(username)
            if(user == null)
                return false

            if(!user.password.contentEquals(password))
                return false

            prefs.setUsername(username)
            prefs.setLoggedIn(true)

            true
        } catch (e: Exception) {
            false
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