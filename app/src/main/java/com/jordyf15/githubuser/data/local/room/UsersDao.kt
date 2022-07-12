package com.jordyf15.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jordyf15.githubuser.data.local.entity.UserEntity

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

//    @Delete
//    suspend fun delete(user: User)

//    @Query("SELECT * FROM user ORDER BY id DESC")
//    fun getAllUsers(): LiveData<List<User>>
}