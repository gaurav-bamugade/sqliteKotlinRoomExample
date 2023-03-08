package com.example.sqlitekotlinroomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sqlitekotlinroomexample.databinding.ActivityMainBinding
import com.example.sqlitekotlinroomexample.db.SubscriberDb
import com.example.sqlitekotlinroomexample.db.SubscriberRepository
import com.example.sqlitekotlinroomexample.db.SubscriberViewModel
import com.example.sqlitekotlinroomexample.db.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao=SubscriberDb.getInstance(application).subscriberDAO
        val repository=SubscriberRepository(dao)
        val factory=SubscriberViewModelFactory(repository)
        subscriberViewModel=ViewModelProvider(this,factory)[SubscriberViewModel::class.java]
        binding.myViewModel=subscriberViewModel
        binding.lifecycleOwner=this
        displaySubscribersList()
    }
    private fun displaySubscribersList(){
        subscriberViewModel.subscriber.observe(this, Observer {
            Log.d("MyTag",it.toString())
        })
    }
}