package com.example.iti_grad_project.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.initializer
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
        private set

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

    companion object {
        // TODO: See if there's a better way to do this
        val CONTEXT_KEY = object : CreationExtras.Key<Context> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[CONTEXT_KEY]
                AuthViewModel(
                    AuthRepository(
                        TODO(),
                        PreferenceManager(context!!)
                    )
                )
            }
        }
    }
}