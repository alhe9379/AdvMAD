package com.example.doggos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DogViewModel: ViewModel() {
    val dogList = MutableLiveData<ArrayList<DogDataModel>>()

    fun updateList(newList:ArrayList<DogDataModel>){
        dogList.value = newList
    }
}