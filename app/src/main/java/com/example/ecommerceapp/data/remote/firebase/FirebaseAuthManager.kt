package com.example.ecommerceapp.data.remote.firebase

import com.example.ecommerceapp.data.remote.model.User
import com.example.ecommerceapp.util.Constants
import com.example.ecommerceapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthManager @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun signUp(email: String, password: String, name: String): Resource<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return Resource.Error("User creation failed")

            val user = User(id = userId, name = name, email = email)
            firestore.collection(Constants.USERS_COLLECTION)
                .document(userId)
                .set(user)
                .await()

            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Sign up failed")
        }
    }

    suspend fun signIn(email: String, password: String): Resource<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return Resource.Error("Sign in failed")

            val userDoc = firestore.collection(Constants.USERS_COLLECTION)
                .document(userId)
                .get()
                .await()

            val user = userDoc.toObject(User::class.java)
                ?: return Resource.Error("User data not found")

            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Sign in failed")
        }
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun getCurrentUserData(): User? {
        val userId = currentUser?.uid ?: return null
        return try {
            firestore.collection(Constants.USERS_COLLECTION)
                .document(userId)
                .get()
                .await()
                .toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }
}