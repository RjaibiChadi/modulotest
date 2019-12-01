package noblur.com.modulotest.data.source.local.userLocal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import noblur.com.modulotest.data.repository.User

@Dao
interface UserDao {


    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)


    @Query("DELETE FROM user")
    fun deleteAllUsers()

}