package com.example.lab9.util

import com.example.lab9.model.DataStoreListItem
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class Database {
    private val db = FirebaseFirestore.getInstance()

    private val ref = db.collection("QandA")

    fun getOptions(): FirestoreRecyclerOptions<DataStoreListItem> {
        val query = ref
        val options = FirestoreRecyclerOptions.Builder<DataStoreListItem>()
            .setQuery(query, DataStoreListItem::class.java)
            .build()
        return options
    }

    fun add(item: DataStoreListItem){
        ref.add(item)
    }

    fun delete(id:String){
        ref.document(id).delete()
    }
}