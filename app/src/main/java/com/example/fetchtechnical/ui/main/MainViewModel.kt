package com.example.fetchtechnical.ui.main

import android.app.Application
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchtechnical.R
import com.example.fetchtechnical.models.ItemModel
import com.example.fetchtechnical.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository, private val application: Application, private val resources: Resources) : ViewModel() {

    val hiringData: MutableLiveData<List<ItemModel>> = MutableLiveData()

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
    
    companion object {
        fun makeResponseUnsuccessfulToast(application: Application, resources: Resources): Toast = Toast.makeText(application, resources.getString(R.string.unsuccessful_response), Toast.LENGTH_SHORT)
    }
}