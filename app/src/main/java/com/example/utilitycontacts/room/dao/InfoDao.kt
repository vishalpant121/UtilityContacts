package com.example.utilitycontacts.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.utilitycontacts.room.entity.Info

@Dao
interface InfoDao {
    @Query("SELECT * FROM info")
    fun getAll(): LiveData<List<Info>>

    @Query("SELECT * FROM info WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): LiveData<List<Info>>

    @Query("SELECT * FROM info WHERE occ LIKE :occupation ")
    fun loadAllByOccupation(occupation: String): LiveData<List<Info>>

    @Query("SELECT * FROM info WHERE loc LIKE :location ")
    fun loadAllByLocation(location: String): LiveData<List<Info>>

    @Query("SELECT * FROM info WHERE loc LIKE :location AND occ LIKE :occupation")
    fun loadAllByLocationAndOccupation(location: String, occupation: String): LiveData<List<Info>>

    @Query("SELECT loc from info")
    fun loadAllLocations(): LiveData<List<String>>

    @Query("SELECT occ from info WHERE loc LIKE :location")
    fun loadAllOccupationOfLocation(location: String): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Info)

    @Delete
    fun delete(user: Info)

    @Query("DELETE FROM info")
    fun deleteAll()

}