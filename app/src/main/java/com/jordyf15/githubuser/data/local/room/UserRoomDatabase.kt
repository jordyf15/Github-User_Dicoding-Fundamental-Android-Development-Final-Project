package com.jordyf15.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jordyf15.githubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UsersDatabase {
            if (INSTANCE == null) {
                synchronized(UsersDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java, "user_database"
                    )
                        .build()
                }
            }
            return INSTANCE as UsersDatabase
        }
    }
}