package com.example.lab9.data

import androidx.lifecycle.*
import com.example.lab9.model.DataStoreListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class DataStoreViewModel(private val dataStoreRepo: DataStoreRepo): ViewModel() {
    val itemList = MutableLiveData<ArrayList<DataStoreListItem>>()
    private var newList = ArrayList<DataStoreListItem>()
    val dataStoreData = dataStoreRepo.readFromDataStore.asLiveData()

    fun saveDataStoreData(question: String, answer: String){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.saveToDataStore(question, answer)
        }
    }

    fun add(item: DataStoreListItem){
        newList.add(item)
        itemList.value = newList
    }

    fun delete(item: DataStoreListItem){
        newList.remove(item)
        itemList.value = newList
    }

//    fun getList(): Flow<DataStoreData> {
//        //return itemList.value
//        return dataStoreData.question
//    }
}

class DataStoreViewModelFactory(private val dataStoreRepo: DataStoreRepo): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        return DataStoreViewModel(dataStoreRepo) as T
    }
}
