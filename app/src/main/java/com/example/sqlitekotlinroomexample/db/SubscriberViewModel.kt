package com.example.sqlitekotlinroomexample.db

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sqlitekotlinroomexample.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private  val repository: SubscriberRepository) : ViewModel(){
    private var isUpdateOrDelete=false
    private lateinit var  subscriberToUpdateOrDelete:Subscriber

    val subscriber=repository.subscribers

    val inputName= MutableLiveData<String>()
    val inputEmail=MutableLiveData<String>()

    val saveOrUpdateButtonText=MutableLiveData<String>()
    val clearAllOrDeleteButtonText=MutableLiveData<String>()

    private val statusMessage=MutableLiveData<Event<String>>()
    val message:LiveData<Event<String>>
    get() = statusMessage

    init{
        saveOrUpdateButtonText.value="save"
        clearAllOrDeleteButtonText.value="clear All"
    }

    fun saveOrUpdate(){
        if(inputName.value==null)
        {
            statusMessage.value= Event("please enter subscriber name")
        }
        else if(inputEmail.value==null)
        {
            statusMessage.value= Event("please enter subscriber email")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches())
        {
            statusMessage.value= Event("please enter correct email address")
        }
        else
        {
            if(isUpdateOrDelete)
            {
                subscriberToUpdateOrDelete.name=inputName.value!!
                subscriberToUpdateOrDelete.email=inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            }
            else
            {
                val name=inputName.value!!
                val email=inputEmail.value!!
                insert(Subscriber(0,name,email))
                inputEmail.value=""
                inputName.value=""
            }
        }



    }
    fun clearAllOrDelete()
    {
        if(isUpdateOrDelete)
        {
            delete(subscriberToUpdateOrDelete)
        }
        else
        {
            clearALl()
        }

    }


    //viewmodel scope.launch = background thread
    private fun insert(subscriber: Subscriber)=viewModelScope.launch(Dispatchers.IO) {
        //indicate number of rows inserted
        val newRowId=repository.insert(subscriber)
        withContext(Dispatchers.Main){
            if(newRowId>-1)
            {
                statusMessage.value= Event("Subscriber Inserted Successfully!! ${newRowId}")
            }
            else
            {
                statusMessage.value= Event("Error occured")

            }

        }
    }
    private fun update(subscriber: Subscriber)=viewModelScope.launch(Dispatchers.IO) {
      //indicate number of rows updated
       val numberOfRows= repository.update(subscriber)
        withContext(Dispatchers.Main)
        {
            if(numberOfRows>0)
            {
                inputName.value=""
                inputEmail.value=""
                isUpdateOrDelete=false
                subscriberToUpdateOrDelete=subscriber
                saveOrUpdateButtonText.value="Save"
                clearAllOrDeleteButtonText.value="ClearAll"
                statusMessage.value=Event("$numberOfRows Subscriber update Successfully")
            }
            else{
                statusMessage.value=Event("Error Occurred")
            }

        }
    }
    private fun delete(subscriber: Subscriber)=viewModelScope.launch(Dispatchers.IO) {
       val numberOfRowsDeleted= repository.delete(subscriber)
        withContext(Dispatchers.Main)
        {
            if(numberOfRowsDeleted>0)
            {
                inputName.value=""
                inputEmail.value=""
                isUpdateOrDelete=false
                subscriberToUpdateOrDelete=subscriber
                saveOrUpdateButtonText.value="Save"
                clearAllOrDeleteButtonText.value="ClearAll"
                statusMessage.value=Event("$numberOfRowsDeleted Subscriber deleted Successfully")
            }
            else
            {
                statusMessage.value=Event("error occurred")

            }

        }
    }
    private fun clearALl()=viewModelScope.launch(Dispatchers.IO) {
        val numberOfRowsDeletedAll=repository.deleteALl()
        withContext(Dispatchers.Main){
           if(numberOfRowsDeletedAll > 0)
           {
               statusMessage.value= Event("$numberOfRowsDeletedAll All Subscriber Deleted Successfully")

           }
            else
           {
               statusMessage.value= Event("Error Occurred")

           }
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber)
    {
        inputName.value=subscriber.name
        inputEmail.value=subscriber.email
        isUpdateOrDelete=true
        subscriberToUpdateOrDelete=subscriber
        saveOrUpdateButtonText.value="Update"
        clearAllOrDeleteButtonText.value="Delete"
    }
}