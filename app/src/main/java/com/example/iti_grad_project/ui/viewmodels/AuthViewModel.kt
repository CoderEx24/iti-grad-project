package com.example.iti_grad_project.ui.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.initializer
import com.example.iti_grad_project.data.local.AppDatabase
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.repositories.AuthRepository
import kotlinx.coroutines.launch

data class AuthActivityUiState(
    val username: String = "",
    val password: String = "",
)

class AuthViewModel(
    private val repository: AuthRepository
): ViewModel() {
    val uiState: MutableLiveData<AuthActivityUiState> = MutableLiveData(AuthActivityUiState())

    var username: String
        set(value) {
            val state = uiState.value
            uiState.postValue(state!!.copy(username = value))
        }
        get() = uiState.value!!.username

    var password: String
        set(value) {
            val state = uiState.value
            uiState.postValue(state!!.copy(password = value))
        }
        get() = uiState.value!!.password

    suspend fun register() = repository.register(username, "dummy@email.com", password)

    suspend fun login() = repository.login(username, password)

    val isLoggedIn: Boolean
        get() = repository.isLoggedIn()

    fun updateProfileImage(imagePath: String) {
        try {
            val username = repository.getUserName()

            Log.i("Username", "$username")
            if (!username.isNullOrEmpty()) {
                viewModelScope.launch {
                    repository.updateProfileImage(username, imagePath)
                }
            }
            Log.i("processimagedone", "$username")
        } catch (e: Exception) {
            Log.i("UpdateError", "Error updating profile image")
        }

        Log.i("UpdatingImageResult", "Done")
    }

    suspend fun getProfileImage(): String? {
        val username = repository.getUserName()
        return if (!username.isNullOrEmpty()) {
            repository.getProfileImage(username)
        } else {
            null
        }
    }

    companion object {
        // TODO: See if there's a better way to do this
        val CONTEXT_KEY = object : CreationExtras.Key<Context> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[CONTEXT_KEY]
                AuthViewModel(
                    AuthRepository(
                        AppDatabase.getDatabase(context!!).userDao(),
                        PreferenceManager(context)
                    )
                )
            }
        }
    }
}