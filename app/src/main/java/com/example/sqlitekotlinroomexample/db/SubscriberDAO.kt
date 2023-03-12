package com.example.sqlitekotlinroomexample.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {

    //rooom doesnt support database access on the main thread. Because uit might lock the ui for long period
    //for that we can use async task, rxjava or executors but the most adavanced easiest and the most efficient way is coroutines
    //since room provides direct support for tkotlin coroutines room facilitates us to write these functions as suspending functions with suspend modifier
    //A suspending function is simple a function that can be paused and resumed at a later time


    //if there is an existing row that has the same id value it will delete it and replace it with new entitiy


    //returns long to check how many row inserted of position
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber):Long

  /*  @Insert
    fun insertSubscriber2(subscriber: Subscriber):Long

    @Insert
    suspend fun insertSubscribers(subscriber1: Subscriber,
                                  subscriber2:Subscriber,
                                  subscriber3:Subscriber):List<Long>

    @Insert
    fun insertSubscriber(subscriber: List<Subscriber>):List<Long>

    @Insert
    fun insertSubscriber(subscriber: Subscriber,subscribers: List<Subscriber>):List<Long>*/

    @Update
    suspend fun  updateSubscriber(subscriber: Subscriber):Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber):Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll():Int

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers():LiveData<List<Subscriber>>

}