package com.example.jaibkharch

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {

    @Query("Select * from TransactionsTable where userid = :userid Order by id DESC")
    fun getallData(userid:String):LiveData<MutableList<TransactionsTable>>


    @Insert(onConflict =OnConflictStrategy.REPLACE)
     fun insertData(transaction: TransactionsTable)


    @Query("Select * from TransactionsTable where userid = :userid Order By id DESC Limit 10")
    fun getRecentTransactions(userid: String):LiveData<MutableList<TransactionsTable>>

    @Query("SELECT SUM(totalspendings) FROM TransactionsTable where userid = :userid")
     fun getTotalSpendings(userid:String): LiveData<Int?>

    @Query("Select SUM(moneyspent) FROM TransactionsTable where userid = :userid")
     fun getOverallSpendings(userid: String):LiveData<Int?>


    @Query("Select category ,Sum(moneyspent) as amount from TransactionsTable where userid=:userid Group by category ")
     fun getchartTransactions(userid: String):LiveData<List<CategoryExpense>>

     @Query("Update TransactionsTable set totalspendings=0 where userid =:userid")
     fun resetTotalSoendingstoZero(userid: String)





 










}