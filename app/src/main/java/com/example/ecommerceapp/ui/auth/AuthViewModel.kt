package com.example.ecommerceapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.remote.model.User
import com.example.ecommerceapp.data.repository.AuthRepository
import com.example.ecommerceapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<Resource<User>?>(null)
    val authState: StateFlow<Resource<User>?> = _authState.asStateFlow()

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            val result = authRepository.signUp(email, password, name)
            _authState.value = result
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            val result = authRepository.signIn(email, password)
            _authState.value = result
        }
    }

    fun resetState() {
        _authState.value = null
    }

    fun isUserLoggedIn(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}
