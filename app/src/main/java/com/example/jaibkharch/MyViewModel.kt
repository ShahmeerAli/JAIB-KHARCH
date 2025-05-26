package com.example.jaibkharch

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale.Category

class MyViewModel(private val repo:Repository, ):ViewModel() {

    fun insert(item:TransactionsTable) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.insert(item)
        }

    }

    fun getRecentTransactions(item:String) :LiveData<MutableList<TransactionsTable>> {
        return repo.getRecentTransactions(item)
    }

     fun getTotalSpendings(item: String) :LiveData<Int?> {
       return repo.getTotalSpendings(item)
    }

    fun getOverallSpendings(item:String) :LiveData<Int?> {
        return repo.getOverallSpendings(item)
    }

    fun getChartTransactions(item:String) :LiveData<List<CategoryExpense>> {
        return repo.getChartTransactions(item)

    }

    fun getAllData(item: String) :LiveData<MutableList<TransactionsTable>> {
       return repo.getAllData(item)
    }

    fun setTotalSoendingsZero(item: String){
        CoroutineScope(Dispatchers.IO).launch {

            repo.resetTotalSpendinstoZero(item)

        }

    }



}