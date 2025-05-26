package com.example.jaibkharch

class Repository ( private val db:TransactionDBHelper){

    fun insert(item:TransactionsTable)=db.transactiondao().insertData(item)

    fun getRecentTransactions(item:String)=db.transactiondao().getRecentTransactions(item)

     fun getTotalSpendings(item: String)=db.transactiondao().getTotalSpendings(item)

     fun getOverallSpendings(item:String)=db.transactiondao().getOverallSpendings(item)

     fun getChartTransactions(userid:String)=db.transactiondao().getchartTransactions(userid)

     fun getAllData(item: String)=db.transactiondao().getallData(item)

     fun resetTotalSpendinstoZero(item:String)=db.transactiondao().resetTotalSoendingstoZero(item)




}