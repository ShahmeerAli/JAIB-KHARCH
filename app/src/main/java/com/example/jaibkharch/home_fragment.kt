package com.example.jaibkharch

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.jaibkharch.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.Permission
import java.util.Calendar


class home_fragment : Fragment() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var datasnap: DataSnapshot
    private lateinit var eddate: EditText
    private lateinit var money: EditText
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var user: FirebaseUser
    private lateinit var viewModel:MyViewModel
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        NotificationPermissions()

        //VIEWMODELS
        val database=TransactionDBHelper.getInstance(requireContext())
        val repository=Repository(database)
        viewModel=ViewModelProvider(this,RepoFactory(repository)).get(MyViewModel::class.java)


        user = FirebaseAuth.getInstance().currentUser!!
        val iduser = user.email!!

        showTransactions(iduser)

        val addBt = view.findViewById<Button>(R.id.addBtHomeFrag)
        var spentMoney = view.findViewById<TextView>(R.id.totalMoneySpent)
        val historyanim=view.findViewById<LottieAnimationView>(R.id.historyanim)

        //DISPLAYING TOTAL SPENDINGS
        resetTotalSpeningds(iduser,requireContext())
        totalspendings(spentMoney, iduser)








        addBt.setOnClickListener {
            //dialogue

            view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(170).withEndAction{
              view.animate().scaleX(1f).scaleY(1f).setDuration(150)
            }

            val dialog = android.app.Dialog(requireContext())
            dialog.setContentView(R.layout.spendingadddialogue)
            money = dialog.findViewById(R.id.edmoneyspent)
            val CategorySpinner = mutableListOf<String>()
            spinner = dialog.findViewById<Spinner>(R.id.spinnercategory)
            val okBt = dialog.findViewById<Button>(R.id.okBt)
            eddate = dialog.findViewById<EditText>(R.id.eddate)
            val adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                CategorySpinner
            )
            CategorySpinner.add("Food")
            CategorySpinner.add("Clothing")
            CategorySpinner.add("Shopping")
            CategorySpinner.add("Rental Payment")
            CategorySpinner.add("Loan Payment")
            CategorySpinner.add("Fares")
            CategorySpinner.add("Health and Fitness")
            CategorySpinner.add("Online Shop/Purchase")
            CategorySpinner.add("Grocery")
            CategorySpinner.add("Fruits and Veges")
            CategorySpinner.add("Stationary")
            CategorySpinner.add("Trips")
            CategorySpinner.add("Dine out")
            CategorySpinner.add("Entertainment")
            CategorySpinner.add("Bills & Utilities")
            CategorySpinner.add("Savings and Insurances")
            CategorySpinner.add("Others")
            spinner.adapter = adapter



            eddate.setOnClickListener {
               val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePicker = DatePickerDialog(
                    requireContext(),
                    { _, selectedYear, selectedMonth, selectedDay ->
                        eddate.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")

                    },
                    year,
                    month,
                    day
                )

                datePicker.show()

            }





            okBt.setOnClickListener {



                try {
                    val moneydata = money.text.toString()
                    val date = eddate.text.toString()
                    val spinnerdate = spinner.selectedItem.toString()


                    var totalmoneySpendings = moneydata.toInt()

                    if (moneydata.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Money Field cannot be Empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        dialog.dismiss()

                        val enterdata=TransactionsTable(
                            spinnerdate,
                            moneydata.toInt(),
                            date,
                            totalmoneySpendings,
                            iduser
                        )

                        viewModel.insert(enterdata)



                    }



                }catch (e:NumberFormatException){
                    e.stackTrace
                    Toast.makeText(requireContext(),"Enter the money in your limits",Toast.LENGTH_SHORT).show()
                }


            }


            dialog.show()


        }

        return view
    }


    private fun showTransactions(user: String) {

        viewModel.getRecentTransactions(user).observe(viewLifecycleOwner){
            transactions->
            val adapter=TransactionAdapter(requireContext())
            recyclerView.adapter=adapter
            adapter.updatelist(transactions)
        }






    }


    private fun totalspendings(spentMoney: TextView, iduser: String) {
        viewModel.getTotalSpendings(iduser).observe(viewLifecycleOwner){
            spendings->
            spentMoney.text="$spendings"
        }
    }


    private fun resetTotalSpeningds(userid: String,context: Context){
        val calendar = Calendar.getInstance()
      val CurrentMonth=calendar.get(Calendar.MONTH)

      val CalenderlastMonth=Calendar.getInstance()
      CalenderlastMonth.add(Calendar.MONTH,-1)
      val lastMonth=CalenderlastMonth.get(Calendar.MONTH)

      sharedPreferences=requireContext().getSharedPreferences("AppPref",Context.MODE_PRIVATE)

      val savedMonth=sharedPreferences.getInt("last_month_checking",-1)

      if(savedMonth!=CurrentMonth){
          viewModel.setTotalSoendingsZero(userid)
          sharedPreferences.edit().putInt("last_month_checking",CurrentMonth).apply()

      }






    }

    private fun NotificationPermissions(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            )!=PackageManager.PERMISSION_GRANTED)

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    108
                )
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 108) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(requireContext(), "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }



}

