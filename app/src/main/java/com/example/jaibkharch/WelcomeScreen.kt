package com.example.jaibkharch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class WelcomeScreen : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome_screen)
        auth=FirebaseAuth.getInstance()

        val signUpBt=findViewById<Button>(R.id.signupbtWelcome)
        val loginBt=findViewById<Button>(R.id.LoginbtWelcome)
        val lottie=findViewById<LottieAnimationView>(R.id.imageWelcoe)
        signUpBt.setOnClickListener {
            startActivity(Intent(this@WelcomeScreen,SignUp::class.java))
            finish()
        }

        loginBt.setOnClickListener {
            startActivity(Intent(this@WelcomeScreen,LoginIn::class.java))
            finish()
        }

    }


    override fun onStart() {
        super.onStart()

        val user: FirebaseUser?=auth.currentUser
        if(user!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}