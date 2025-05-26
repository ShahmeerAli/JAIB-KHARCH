package com.example.jaibkharch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(var context: Context):RecyclerView.Adapter<TransactionAdapter.MyviewHolder>() {

    private var transactions: MutableList<TransactionsTable> = mutableListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.MyviewHolder {
        val item=LayoutInflater.from(parent.context).inflate(R.layout.add_recycler,parent,false)

        return MyviewHolder(item)


    }

    override fun onBindViewHolder(holder: TransactionAdapter.MyviewHolder, position: Int) {

        val transaction=transactions[position]
        holder.category.text=transaction.category
        holder.moneytxt.text=transaction.moneyspent.toString()
        holder.date.text=transaction.date



    }

    override fun getItemCount(): Int {
        return transactions.size
    }


    fun updatelist(newlist:List<TransactionsTable>){
        transactions.clear()
        transactions.addAll(newlist)
        notifyDataSetChanged()
    }



    class MyviewHolder(item: View):RecyclerView.ViewHolder(item){
        val category=item.findViewById<TextView>(R.id.recyclertxtCategory)
        val date=item.findViewById<TextView>(R.id.recyclerdateTxt)
        val moneytxt=item.findViewById<TextView>(R.id.recyclertxtSpending)



    }
}