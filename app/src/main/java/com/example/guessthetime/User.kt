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
    val password: String
    ) {
    constructor() : this(0, "","","","","")
}

object DataProvider {
    val users = listOf(
        User(0,"Jan","Kowalski","jan.kowalski@gmail.com","jan13","test123"),
        User(0,"Katarzyna","Byk","kasialol@gmail.com","kbyk11","haslo11"),
        User(0,"Marcin","Kowal","marcinkkk@xd.com","marcinxx","passwd89"),
    )
    val user: User
        get() = User(0,"","","","","")
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

}
