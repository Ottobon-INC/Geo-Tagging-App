package com.orcalabs.hrms.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.Employee
import com.orcalabs.hrms.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "sandeep.reddy@orcalabs.in",
    val password: String = "sandeep.reddy@orcalabs.in",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loggedInUser: Employee? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                _uiState.value = _uiState.value.copy(loggedInUser = user)
            }
        }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail, errorMessage = null)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null)
    }

    fun fillQuickUser(email: String) {
        _uiState.value = _uiState.value.copy(email = email, password = email, errorMessage = null)
    }

    fun login(onSuccess: (Employee) -> Unit) {
        val email = _uiState.value.email.trim()
        val pass = _uiState.value.password.trim()

        if (email.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Please enter your official email")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            val result = authRepository.login(email, pass)
            result.onSuccess { emp ->
                _uiState.value = _uiState.value.copy(isLoading = false, loggedInUser = emp)
                onSuccess(emp)
            }.onFailure { err ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = err.message ?: "Authentication failed"
                )
            }
        }
    }
}
