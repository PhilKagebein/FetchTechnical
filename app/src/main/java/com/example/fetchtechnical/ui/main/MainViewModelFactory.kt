package com.example.fetchtechnical.ui.main

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fetchtechnical.repository.Repository

class MainViewModelFactory(
    private val repository: Repository,
    private val application: Application,
    private val resources: Resources
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, application, resources) as T
    }

}