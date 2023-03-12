package com.example.sqlitekotlinroomexample.db

class SubscriberRepository(private val dao:SubscriberDAO) {

    val subscribers=dao.getAllSubscribers()


    //returns long to check how many row inserted of position
    suspend fun  insert(subscriber: Subscriber):Long{
       return dao.insertSubscriber(subscriber)
    }

    suspend fun  update(subscriber: Subscriber):Int{
       return dao.updateSubscriber(subscriber)
    }


    suspend fun  delete(subscriber: Subscriber):Int{
        return dao.deleteSubscriber(subscriber)
    }


    suspend fun  deleteALl():Int{
        return dao.deleteAll()
    }
}