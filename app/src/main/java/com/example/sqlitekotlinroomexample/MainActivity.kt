package com.example.sqlitekotlinroomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitekotlinroomexample.databinding.ActivityMainBinding
import com.example.sqlitekotlinroomexample.db.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter:MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao=SubscriberDb.getInstance(application).subscriberDAO
        val repository=SubscriberRepository(dao)
        val factory=SubscriberViewModelFactory(repository)
        subscriberViewModel=ViewModelProvider(this,factory)[SubscriberViewModel::class.java]
        binding.myViewModel=subscriberViewModel
        binding.lifecycleOwner=this
        initRecyclerView()
        subscriberViewModel.message.observe(this,{
            //avoid repititions
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Earlier we created a new adapter object for every new update
    //now we are just using the initially created adapter object
    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager=LinearLayoutManager(this)
        adapter=MyRecyclerViewAdapter ({selectedItem:Subscriber->listItemClicked(selectedItem)})
        binding.subscriberRecyclerView.adapter=adapter
        displaySubscribersList()
    }
    private fun displaySubscribersList(){
        subscriberViewModel.subscriber.observe(this, Observer {
            Log.d("MyTag",it.toString())
           /* binding.subscriberRecyclerView.adapter=MyRecyclerViewAdapter(it,{
                    selectedItem:Subscriber->listItemClicked(selectedItem)})*/
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
    private fun listItemClicked(subscirber:Subscriber)
    {
        Log.d("subscirber","subscirber")
        subscriberViewModel.initUpdateAndDelete(subscirber)
    }
}

//everytime we create a new subscriber update a subscriber or delete a subscriber we create a
// new MyrecyclerViewAdapter object we should have written codes to create a one adapter object and update it for database changes
/*
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
        initRecyclerView()
        subscriberViewModel.message.observe(this,{
            //avoid repititions
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager=LinearLayoutManager(this)
        displaySubscribersList()
    }
    private fun displaySubscribersList(){
        subscriberViewModel.subscriber.observe(this, Observer {
            Log.d("MyTag",it.toString())
            binding.subscriberRecyclerView.adapter=MyRecyclerViewAdapter(it,{selectedItem:Subscriber->listItemClicked(selectedItem)})
        })
    }
    private fun listItemClicked(subscirber:Subscriber)
    {
        Log.d("subscirber","subscirber")
        subscriberViewModel.initUpdateAndDelete(subscirber)
    }
}*/
