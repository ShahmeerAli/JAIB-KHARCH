package com.example.jaibkharch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.core.animateDecay
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class insights_fragment : Fragment() {

    private lateinit var viewModel: MyViewModel
    private lateinit var auth: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insights_fragment, container, false)
        val piechartid = view.findViewById<PieChart>(R.id.piechart)

        auth = FirebaseAuth.getInstance().currentUser!!
        val userid = auth.email!!


        //viewmodel classes
        val database = TransactionDBHelper.getInstance(requireContext())
        val repository = Repository(database)
        val repofactory = RepoFactory(repository)

        viewModel = ViewModelProvider(this, repofactory).get(MyViewModel::class.java)

        viewModel.getChartTransactions(userid).observe(viewLifecycleOwner) { data ->

            if(data.isNullOrEmpty()){
                Toast.makeText(requireContext(),"No Data found",Toast.LENGTH_SHORT).show()
                return@observe
            }

            val piedatalist = ArrayList<PieEntry>()
            data.forEach { transactions ->

                piedatalist.add(PieEntry(transactions.amount, transactions.category))


            }

            val pieDataset=PieDataSet(piedatalist,"Category wise Breakdown")
            pieDataset.setColors(
             ColorTemplate.MATERIAL_COLORS,255
            )
            pieDataset.valueTextSize = 14f

            val legend=piechartid.legend
            legend.orientation=Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment=Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment=Legend.LegendHorizontalAlignment.CENTER
            legend.setDrawInside(false)
            legend.textSize=10f
            legend.formSize=8f
            legend.yEntrySpace=12f


            val piedata=PieData(pieDataset)
            piechartid.data=piedata
            piechartid.description.isEnabled=false
            piechartid.animateY(100)
            piechartid.centerText="Categories"
            piechartid.setDrawEntryLabels(true)
            piechartid.setEntryLabelTextSize(14f)
            piechartid.transparentCircleRadius=60f
            piechartid.holeRadius=50f
            piechartid.invalidate()



        }



        return view
        }


    }