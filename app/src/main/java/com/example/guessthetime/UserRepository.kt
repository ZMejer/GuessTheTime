package com.example.guessthetime

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserRepository(private val userDao: UserDao, private val application: Application) {

    fun getUsers() = userDao.getUsers()
    private val userPreferences = UserPreferences(application)
    suspend fun add(user: User) = userDao.insert(user)

    fun validateLogin(userLogin: String, userPassword: String): Flow<List<User>> =
        userDao.validateLogin(userLogin, userPassword)

    suspend fun logIn(isUserLoggedIn: Boolean) {
        userPreferences.storeLogged(application, isUserLoggedIn)
    }

    suspend fun logout() {
        logIn(false)
    }

    fun getUserById(userId : Int) = userDao.getById(userId)

    suspend fun storeUser(userId: Int) {
        userPreferences.storeUserId(application, userId)
    }

    suspend fun storeUserPoints(points: Int) {
        userPreferences.storeUserPoints(application, points)
    }

    suspend fun addAll(users: List<User>) {
        for (user in users) {
            add(user)
        }
    }

    fun getLeaderboard() = userDao.getLeaderboard()

    suspend fun updatePoints(userId: Int, newPoints: Int) {
        userDao.updatePoints(userId, newPoints)
    }

    suspend fun getUserPoints(userId: Int): Int {
        return userDao.getUserPoints(userId)
    }

    fun getUserIdFlow(): Flow<Int> {
        return userPreferences.getUserIdFlow(application)
    }
}

