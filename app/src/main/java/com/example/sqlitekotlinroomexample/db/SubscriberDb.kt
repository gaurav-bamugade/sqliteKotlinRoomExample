package com.example.sqlitekotlinroomexample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[Subscriber::class],version=1)
abstract class SubscriberDb: RoomDatabase() {
    abstract val subscriberDAO:SubscriberDAO
    companion object{
        @Volatile
        private var INSTANCE:SubscriberDb?=null

        fun getInstance(context: Context):SubscriberDb{
            synchronized(this)
            {
                var instance:SubscriberDb?=INSTANCE
                if(instance==null)
                {
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDb::class.java,
                        "subscriber_data_database"
                    ).build()
                }
                return instance
            }

        }

    }

}