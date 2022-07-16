package com.jordyf15.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jordyf15.githubuser.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)

//    @Delete
//    suspend fun delete(user: User)

//    @Query("SELECT * FROM user ORDER BY id DESC")
//    fun getAllUsers(): LiveData<List<User>>
}