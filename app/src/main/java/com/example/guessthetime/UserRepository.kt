package com.example.guessthetime

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    fun getUsers() = userDao.getUsers()

    suspend fun clear() = userDao.deleteAll()

    suspend fun add(user: User) = userDao.insert(user)

    suspend fun addAll(users: List<User>) {
        users.forEach { user ->
            userDao.insert(user)
        }
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
    }

    // suspend fun delete(id: Int) = gradeDao.deleteById(id)

    suspend fun delete(user: User) = userDao.delete(user)

    suspend fun update(user: User) = userDao.update(user)

    fun validateLogin(userLogin: String, userPassword: String): Flow<List<User>> = userDao.validateLogin(userLogin, userPassword)
}
