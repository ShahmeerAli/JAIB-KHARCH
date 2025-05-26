package com.example.jaibkharch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class transaction_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel:MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_transaction_fragment, container, false)

        val overallmoney=view.findViewById<TextView>(R.id.moneyspentHistory)
        val user:FirebaseUser=FirebaseAuth.getInstance().currentUser!!
        val userid=user.email!!
        val databasehelper=TransactionDBHelper.getInstance(requireContext())
        val repo=Repository(databasehelper)
        viewModel=ViewModelProvider(this,RepoFactory(repo)).get(MyViewModel::class.java)






        recyclerView=view.findViewById<RecyclerView>(R.id.recyclerTransaction)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

       getoverallSpendings(userid,overallmoney)
        showTransactions(userid)





        return view

    }


    private fun showTransactions(user:String){

        viewModel.getAllData(user).observe(viewLifecycleOwner){
            transactions->
            val adapter=TransactionAdapter(requireContext())
            recyclerView.adapter=adapter
            adapter.updatelist(transactions)
        }

    }


    private fun getoverallSpendings(user:String,money:TextView){
        viewModel.getOverallSpendings(user).observe(viewLifecycleOwner){
            totalmoney ->
            if(totalmoney.toString().equals(null)){
                money.text="0"
            }else{
                money.text="$totalmoney"
            }

        }

    }





}