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
    private val _usersState = MutableStateFlow<List<User>>(emptyList())
    val usersState: StateFlow<List<User>> get() = _usersState

    private val _loginResult = MutableStateFlow<List<User>>(emptyList())
    val loginResult: StateFlow<List<User>> = _loginResult

    private val _isUserLoggedIn = mutableStateOf(false)
    val isUserLoggedIn: State<Boolean> = _isUserLoggedIn

    init {
        val db = UserDatabase.getDatabase(application)
        val dao = db.userDao()
        repository = UserRepository(dao)
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            repository.getUsers().collect { users ->
                _usersState.value = users
            }
        }
    }

    fun getUserById(id: Int): User? {
        return _usersState.value.find { it.id == id }
    }

    fun clearUsers() {
        viewModelScope.launch {
            repository.clear()
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            repository.add(user)
        }
    }

    fun addAllUsers(users: List<User>) {
        viewModelScope.launch {
            repository.addAll(users)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.delete(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.update(user)
        }
    }

    fun validateLogin(login: String, password: String) {
        viewModelScope.launch {
            repository.validateLogin(login, password).collect { users ->
                _loginResult.value = users
                checkLogin()
            }
        }
    }

    private fun checkLogin() {
        val isValid = _loginResult.value.isNotEmpty()
        _isUserLoggedIn.value = isValid
        if (isValid) {
            _loginResult.value = emptyList()
        }
    }
}
