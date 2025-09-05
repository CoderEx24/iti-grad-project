package com.example.iti_grad_project.ui.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.initializer
import com.example.iti_grad_project.data.local.AppDatabase
import com.example.iti_grad_project.data.prefs.PreferenceManager
import com.example.iti_grad_project.repositories.AuthRepository

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