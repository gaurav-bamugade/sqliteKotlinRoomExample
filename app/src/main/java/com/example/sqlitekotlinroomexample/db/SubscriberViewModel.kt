package com.example.sqlitekotlinroomexample.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriberViewModel(private  val repository: SubscriberRepository) : ViewModel(){
    val subscriber=repository.subscribers

    val inputName= MutableLiveData<String>()
    val inputEmail=MutableLiveData<String>()

    val saveOrUpdateButtonText=MutableLiveData<String>()
    val clearAllOrDeleteButtonText=MutableLiveData<String>()

    init{
        saveOrUpdateButtonText.value="save"
        clearAllOrDeleteButtonText.value="clear All"
    }

    fun saveOrUpdate(){
        val name=inputName.value!!
        val email=inputEmail.value!!
        insert(Subscriber(0,name,email))
        inputEmail.value=""
        inputName.value=""
    }
    fun clearAllOrDelete()
    {
        clearALl()
    }


    //viewmodel scope.launch = background thread
    fun insert(subscriber: Subscriber)=viewModelScope.launch(Dispatchers.IO) {
        repository.insert(subscriber)
    }
    fun update(subscriber: Subscriber)=viewModelScope.launch(Dispatchers.IO) {
        repository.update(subscriber)
    }
    fun delete(subscriber: Subscriber)=viewModelScope.launch(Dispatchers.IO) {
        repository.delete(subscriber)
    }
    fun clearALl()=viewModelScope.launch(Dispatchers.IO) {
        repository.deleteALl()
    }
}