package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDtoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserDto): Long

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<UserDto?>

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUserById(id: Long)

}