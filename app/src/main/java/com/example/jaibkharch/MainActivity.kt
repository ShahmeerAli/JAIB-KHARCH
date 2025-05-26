package com.example.jaibkharch

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val frameLayout=findViewById<FrameLayout>(R.id.framelayout)
        val bottomNav=findViewById<BottomNavigationView>(R.id.BottomNav)

        CreateFragment(home_fragment(),home_fragment::class.java.name)


        bottomNav.setOnItemSelectedListener {
            item->
            val option=item.itemId
            when(option){
                R.id.navHome ->{
                    CreateFragment(home_fragment(),home_fragment::class.java.name)
                    true
                }

                R.id.NavInsights -> {
                    CreateFragment(insights_fragment())
                    true
                }

                R.id.navTrans ->{
                    CreateFragment(transaction_fragment())
                    true
                }

                else -> {
                    CreateFragment(profile_fragment())
                    true

                }


            }
        }



    }


    private fun CreateFragment(frag:Fragment,tag:String?=null){
        if(frag is home_fragment){
            supportFragmentManager.popBackStack(tag,FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        val fragmanager=supportFragmentManager
        val fragTransaction=fragmanager.beginTransaction()
        fragTransaction.replace(R.id.framelayout,frag)
        fragTransaction.addToBackStack(tag)

        fragTransaction.commit()



    }
}