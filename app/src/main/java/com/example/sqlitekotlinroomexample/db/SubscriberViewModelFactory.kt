package com.example.sqlitekotlinroomexample.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl

class SubscriberViewModelFactory(private val repository: SubscriberRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if(modelClass.isAssignableFrom(SubscriberViewModel::class.java))
      {
          return SubscriberViewModel(repository) as T
      }
        throw java.lang.IllegalArgumentException("UnKnow viewModel class")
    }

}