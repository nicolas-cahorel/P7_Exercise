package com.openclassrooms.arista.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

@Database(entities = [UserDto::class, SleepDto::class, ExerciseDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDtoDao(): UserDtoDao
    abstract fun sleepDtoDao(): SleepDtoDao
    abstract fun exerciseDtoDao(): ExerciseDtoDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.sleepDtoDao(), database.userDtoDao())
                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AristaDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }


        suspend fun populateDatabase(sleepDao: SleepDtoDao, userDtoDao: UserDtoDao) {

            val startTime = LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
            val durationSleep1 = 420
            val durationSleep2 = 360
            val durationSleep3 = 480
            val minToMilli = 60*1000

            // Sleep Data insertion
            sleepDao.insertSleep(
                SleepDto(
                    startTime = startTime, endTime = startTime + durationSleep1 * minToMilli, duration = durationSleep1, quality = 8
                )
            )
            sleepDao.insertSleep(
                SleepDto(
                    startTime = startTime, endTime = startTime + durationSleep2 * minToMilli, duration = durationSleep2, quality = 5
                )
            )
            sleepDao.insertSleep(
                SleepDto(
                    startTime = startTime, endTime = startTime + durationSleep3 * minToMilli, duration = durationSleep3, quality = 9
                )
            )

            // User Data insertion
            userDtoDao.insertOrUpdateUser(
                UserDto(
                    name = "Jim Nastyk",
                    email = "jim.nasyk@jo2024.fr",
                    password = "password"
                )
            )
        }
    }
}