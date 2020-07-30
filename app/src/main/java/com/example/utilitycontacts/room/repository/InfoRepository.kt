package com.example.utilitycontacts.room.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.utilitycontacts.room.dao.InfoDao
import com.example.utilitycontacts.room.entity.Info

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class InfoRepository(private val infoDao: InfoDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allItems: LiveData<List<Info>> = infoDao.getAll()

    fun loadAllByIds(userIds: IntArray) : LiveData<List<Info>> {
        return infoDao.loadAllByIds(userIds)
    }

    fun loadAllByOccupation(occupation : String) : LiveData<List<Info>> {
        return infoDao.loadAllByOccupation(occupation)
    }

    fun loadAllByLocation(location : String) : LiveData<List<Info>> {
        return infoDao.loadAllByLocation(location)
    }

    fun loadAllByLocationAndOccupation(location : String, occupation : String) : LiveData<List<Info>> {
        return infoDao.loadAllByLocationAndOccupation(location, occupation)
    }

    fun loadAllLocations(): LiveData<List<String>> {
        return infoDao.loadAllLocations()
    }

    fun loadAllOccupationOfLocation(location: String): LiveData<List<String>> {
        return infoDao.loadAllOccupationOfLocation(location)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: Info) {
        infoDao.insert(item)
    }
}