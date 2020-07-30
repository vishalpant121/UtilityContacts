package com.example.utilitycontacts.ui.infoList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.utilitycontacts.model.DetailsItem
import com.example.utilitycontacts.model.FirebaseResponse
import com.example.utilitycontacts.room.database.InfoDatabase
import com.example.utilitycontacts.room.entity.Info
import com.example.utilitycontacts.room.repository.InfoRepository
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InfoRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allItems: LiveData<List<Info>>
    private val firebaseRef : DatabaseReference

    init {
        val infoDao = InfoDatabase.getDatabase(application, viewModelScope).infoDao()
        repository = InfoRepository(infoDao)
        allItems = repository.allItems
        val database = FirebaseDatabase.getInstance()
        firebaseRef = database.reference
    }

    fun loadAllByIds(userIds: IntArray) : LiveData<List<Info>> {
        return repository.loadAllByIds(userIds)
    }

    fun loadAllByOccupation(occupation : String) : LiveData<List<Info>> {
        return repository.loadAllByOccupation(occupation)
    }

    fun loadAllByLocation(location : String) : LiveData<List<Info>> {
        return repository.loadAllByLocation(location)
    }

    fun loadAllByLocationAndOccupation(location : String, occupation : String) : LiveData<List<Info>> {
        return repository.loadAllByLocationAndOccupation(location, occupation)
    }


    fun loadAllLocations(): LiveData<List<String>> {
        return repository.loadAllLocations()
    }

    fun loadAllOccupationOfLocation(location: String): LiveData<List<String>> {
        return repository.loadAllOccupationOfLocation(location)
    }

    fun insert(info: Info) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(info)
    }

    fun saveDetailsInInfoEntity(details : DetailsItem) {
        val info = Info(address = details.address,
        altPhone = details.altPhone, from = details.from, to = details.to, name = details.name,
        occ = details.occ, phone = details.phone, loc = details.loc, id = 0)

        this.insert(info)
    }

    fun readFirebaseDb() = GlobalScope.launch(Dispatchers.IO) {
        // Read from the database
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                val response: FirebaseResponse? = dataSnapshot.getValue(FirebaseResponse::class.java)
                Log.d("FragmentActivity.TAG", "Value is: $value")

                for (item in response?.base!!) {
                    saveDetailsInInfoEntity(item!!)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FragmentActivity.TAG", "Failed to read value.", error.toException())
            }
        })

    }
}