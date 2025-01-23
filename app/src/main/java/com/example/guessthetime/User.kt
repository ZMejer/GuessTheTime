package com.example.guessthetime
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val surname: String,
    val email: String,
    val login: String,
    val password: String,
    val points: Int
    ) {
    constructor() : this(0, "","","","","",0)
}

object DataProvider {
    val users = listOf(
        User(0,"Jan","Kowalski","jan.kowalski@gmail.com","jan13","test123",0),
        User(0,"Katarzyna","Byk","kasialol@gmail.com","kbyk11","haslo11",0),
        User(0,"Marcin","Kowal","marcinkkk@xd.com","marcinxx","passwd89",0),
    )
    val user: User
        get() = User(0,"","","","","",0)
}

@Dao
interface UserDao {

    @Query("SELECT * FROM users_table")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("DELETE FROM users_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(user: User)

    @Update
    suspend fun update(user: User)

    @Query("DELETE FROM users_table WHERE id=:userId")
    fun deleteById(userId : Int)

    @Query("SELECT * FROM users_table WHERE login=:userLogin AND password=:userPassword")
    fun validateLogin(userLogin: String, userPassword: String): Flow<List<User>>

    @Query("SELECT * FROM users_table WHERE id=:userId")
    fun getById(userId : Int): Flow<User>

    @Query("UPDATE users_table SET points = :newPoints WHERE id = :userId")
    suspend fun updatePoints(userId: Int, newPoints: Int)

    @Query("SELECT * FROM users_table WHERE points > 0 ORDER BY points DESC LIMIT 10\n")
    fun getLeaderboard(): Flow<List<User>>

    @Query("SELECT points FROM users_table WHERE id = :userId LIMIT 1")
    suspend fun getUserPoints(userId: Int): Int

}
