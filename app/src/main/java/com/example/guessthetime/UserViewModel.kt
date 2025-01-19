package com.example.guessthetime
import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State


class UserViewModel(application: Application) : ViewModel() {

    private val repository: UserRepository
    private val userPreferences = UserPreferences(application.applicationContext)

    private val _usersState = MutableStateFlow<List<User>>(emptyList())
    val usersState: StateFlow<List<User>> get() = _usersState

    private val _loginResult = MutableStateFlow<List<User>>(emptyList())
    val loginResult: StateFlow<List<User>> = _loginResult

    private val _isUserLoggedIn = userPreferences.getLoggedFlow(application)
    val isUserLoggedIn: Flow<Boolean> get() = _isUserLoggedIn

    private val _userId = userPreferences.getUserIdFlow(application)
    val userId: Flow<Int> get() = _userId

    init {
        val db = UserDatabase.getDatabase(application)
        val dao = db.userDao()
        repository = UserRepository(dao, application)
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            repository.getUsers().collect { users ->
                _usersState.value = users
            }
        }
    }


    fun addUser(user: User) {
        viewModelScope.launch {
            repository.add(user)
        }
    }

    fun validateLogin(login: String, password: String) {
        viewModelScope.launch {
            repository.validateLogin(login, password).collect { users ->
                if (users.isNotEmpty()) {
                    repository.logIn(true)
                    repository.storeUser(users[0].id)
                } else {
                    repository.logIn(false)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logIn(false)
        }
    }

    fun getUserById(userId : Int) = repository.getUserById(userId)
}