package com.example.jaibkharch

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [TransactionsTable::class],
    exportSchema = false,
    version = 15
)
abstract class TransactionDBHelper:RoomDatabase() {



    abstract fun transactiondao():TransactionDao



    companion object{
        val DB_NAME="transaction_db"

        @Volatile
        private var INSTANCE:TransactionDBHelper?=null

        fun getInstance(context: Context):TransactionDBHelper{

            return INSTANCE?: synchronized(this){

                val instance= Room.databaseBuilder(context.applicationContext,TransactionDBHelper::class.java,
                    DB_NAME).fallbackToDestructiveMigration().build()
                INSTANCE=instance

                instance

            }


        }





    }

}