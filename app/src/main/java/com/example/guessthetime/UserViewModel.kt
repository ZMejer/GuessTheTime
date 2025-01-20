package com.example.guessthetime
import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import kotlin.math.roundToInt
import kotlin.random.Random


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

    private val _hours = MutableStateFlow(Random.nextInt(0, 13))
    val hours: StateFlow<Int> get() = _hours

    private val _minutes = MutableStateFlow(Random.nextInt(0, 60))
    val minutes: StateFlow<Int> get() = _minutes

    private val _seconds = MutableStateFlow(Random.nextInt(0, 60))
    val seconds: StateFlow<Int> get() = _seconds

    private val _userPoints = MutableStateFlow(0)
    val userPoints: StateFlow<Int> get() = _userPoints

    init {
        val db = UserDatabase.getDatabase(application)
        val dao = db.userDao()
        repository = UserRepository(dao, application)
        fetchUsers()
        fetchUserPoints()
    }

    private fun fetchUserPoints() {
        viewModelScope.launch {
            userPreferences.getUserPointsFlow().collect { points ->
                _userPoints.value = points
            }
        }
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

    fun addAllUsers(users: List<User>) {
        viewModelScope.launch {
            repository.addAll(users)
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

    fun updateTime() {
        _hours.value = Random.nextInt(1,13)
        _minutes.value = Random.nextInt(0,60)
        _seconds.value = Random.nextInt(0,60)
    }

    fun calculateAccuracy(answeredHour: Int, answeredMinute: Int, answeredSecond: Int, correctHour: Int, correctMinute: Int, correctSecond: Int) : Double {
        val hourDifference = Math.abs(answeredHour - correctHour)
        val maxHourDifference = 23
        val hourAccuracy = ((maxHourDifference - hourDifference) / maxHourDifference.toDouble()) * 100
        val minuteDifference = Math.abs(answeredMinute - correctMinute)
        val maxDifference = 59
        val minuteAccuracy = ((maxDifference - minuteDifference) / maxDifference.toDouble()) * 100
        val secondDifference = Math.abs(answeredSecond - correctSecond)
        val secondAccuracy = ((maxDifference - secondDifference) / maxDifference.toDouble()) * 100
        val accuracy = (hourAccuracy + minuteAccuracy + secondAccuracy) / 3
        if (answeredHour == -1 || answeredMinute == -1 || answeredSecond == -1 || correctHour == -1 || correctMinute == -1 || correctSecond == -1 ) {
            return -1.0
        }
        return String.format("%.2f", accuracy).toDouble()
    }

    fun calculatePoints(accuracy : Double) : Int {
        val roundedAccuracy = "%.2f".format(accuracy).toDouble()
        return when (roundedAccuracy) {
            in 0.0..80.0 -> 0
            in 81.0..90.0 -> 1
            in 91.0..95.0 -> 5
            in 96.0..100.0 -> 10
            else -> 0
        }
    }

    fun updatePoints(userId: Int, newPoints: Int) {
        viewModelScope.launch {
            repository.updatePoints(userId, newPoints)
            userPreferences.storeUserPoints(newPoints)
        }
    }

    fun getUserPoints(userId: Int) {
        viewModelScope.launch {
            repository.getUserById(userId).collect { user ->
                _userPoints.value = user.points
                userPreferences.storeUserPoints(user.points)
            }
        }
    }
}