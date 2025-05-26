package com.example.jaibkharch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RepoFactory(private val repo:Repository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(repo) as T
    }
}