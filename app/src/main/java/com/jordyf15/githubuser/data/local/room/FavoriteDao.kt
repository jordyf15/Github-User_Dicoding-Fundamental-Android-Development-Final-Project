package com.jordyf15.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jordyf15.githubuser.data.local.entity.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE username = :username")
    fun deleteFavorite(username: String)

    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username)")
    fun checkIsFavorite(username: String): LiveData<Boolean>
}