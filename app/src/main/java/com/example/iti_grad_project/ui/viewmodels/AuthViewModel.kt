package com.example.iti_grad_project.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
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
        val Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val viewModel = AuthViewModel(
                    AuthRepository(
                        TODO(),
                        TODO()
                    )
                )

                return viewModel as T
            }
        }
    }
}