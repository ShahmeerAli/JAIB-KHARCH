package com.example.jaibkharch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.w3c.dom.Text
import kotlin.math.log


class profile_fragment : Fragment() {


    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth=FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_profile_fragment, container, false)
        val logoutBt=view.findViewById<Button>(R.id.logoutBT)

        logoutBt.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(),LoginIn::class.java))
             requireActivity().finish()
        }

        val txt=view.findViewById<TextView>(R.id.emailusertxt)
        val useremail:FirebaseUser=FirebaseAuth.getInstance().currentUser!!
        val email=useremail.email.toString()

        txt.setText(email)



       return view
    }


}