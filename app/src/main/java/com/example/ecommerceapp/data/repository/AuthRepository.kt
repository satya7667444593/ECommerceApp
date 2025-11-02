package com.example.ecommerceapp.data.repository
import com.example.ecommerceapp.data.remote.firebase.FirebaseAuthManager
import com.example.ecommerceapp.data.remote.model.User
import com.example.ecommerceapp.util.Resource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authManager: FirebaseAuthManager
) {
    val currentUser: FirebaseUser?
        get() = authManager.currentUser

    suspend fun signUp(email: String, password: String, name: String): Resource<User> {
        return authManager.signUp(email, password, name)
    }

    suspend fun signIn(email: String, password: String): Resource<User> {
        return authManager.signIn(email, password)
    }

    fun signOut() {
        authManager.signOut()
    }

    suspend fun getCurrentUserData(): User? {
        return authManager.getCurrentUserData()
    }

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }
}