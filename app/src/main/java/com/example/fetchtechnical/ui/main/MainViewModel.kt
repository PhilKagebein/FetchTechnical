package com.example.fetchtechnical.ui.main

import android.app.Application
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.*
import com.example.fetchtechnical.R
import com.example.fetchtechnical.models.ItemModel
import com.example.fetchtechnical.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, private val application: Application, private val resources: Resources) : ViewModel() {

    private val hiringData: MutableLiveData<List<ItemModel>> = MutableLiveData()

    fun getFetchHiringData() {
        viewModelScope.launch {
            val response = repository.getFetchHiringData()

            if (response.isSuccessful) {
                hiringData.postValue(response.body())
            } else {
                makeResponseUnsuccessfulToast(application, resources).show()
            }

        }
    }

    fun formatHiringData(): LiveData<List<ItemModel>> = hiringData.map { itemsList ->
        val filteredItemsMap = filterOutNullAndBlankEntries(itemsList)
        sortByName(filteredItemsMap)
    }

    private fun filterOutNullAndBlankEntries(itemsList: List<ItemModel>): MutableMap<Int, MutableList<ItemModel>> {
        val itemsMap = mutableMapOf<Int, MutableList<ItemModel>>()

        for (item in itemsList) {

            if ( !item.name.isNullOrEmpty() ) {
                if (itemsMap.containsKey(item.listId)) {
                    itemsMap[item.listId]?.add(item)
                } else {
                    itemsMap[item.listId] = mutableListOf(item)
                }
            }

        }

        return itemsMap
    }

    private fun sortByName(filteredItemsMap: MutableMap<Int, MutableList<ItemModel>>): MutableList<ItemModel> {
        val sortedItemsList = mutableListOf<ItemModel>()

        for (key in filteredItemsMap.keys.sorted()) {
            filteredItemsMap[key]?.let { it ->
                sortedItemsList.addAll(it.sortedWith { firstItem, secondItem -> getIntFromString(firstItem?.name) - getIntFromString(secondItem?.name) })
            }
        }

        return sortedItemsList
    }

    private fun getIntFromString(string: String?): Int {
        val int = string?.replace("\\D".toRegex(), "")?.toInt()
        int?.let { return int } ?: run { return 0 }
    }
    
    companion object {
        fun makeResponseUnsuccessfulToast(application: Application, resources: Resources): Toast = Toast.makeText(application, resources.getString(R.string.unsuccessful_response), Toast.LENGTH_SHORT)
    }
}