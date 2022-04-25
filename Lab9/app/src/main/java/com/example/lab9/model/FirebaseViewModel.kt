package com.example.lab9.model

import androidx.lifecycle.ViewModel
import com.example.lab9.util.Database

class FirebaseViewModel: ViewModel() {
    private val db = Database()
    val options = db.getOptions()

    fun addToDb(item: DataStoreListItem){
        db.add(item)
    }

    fun delete(id: String){
        db.delete(id)
    }
}