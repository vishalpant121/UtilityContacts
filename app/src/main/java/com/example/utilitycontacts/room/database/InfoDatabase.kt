package com.example.utilitycontacts.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.utilitycontacts.room.dao.InfoDao
import com.example.utilitycontacts.room.entity.Info
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Info::class], version = 1,  exportSchema = false)
abstract class InfoDatabase : RoomDatabase() {
    abstract fun infoDao(): InfoDao
    companion object {
        @Volatile
        private var INSTANCE: InfoDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope
        ): InfoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InfoDatabase::class.java,
                    "tradesman_info_database"
                ).addCallback(InfoDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class InfoDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.infoDao())
                }
            }
        }

        suspend fun populateDatabase(infoDao: InfoDao) {
            // Delete all content here.
            infoDao.deleteAll()
        }

    }
}
