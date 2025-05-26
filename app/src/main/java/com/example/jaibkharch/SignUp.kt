package com.example.jaibkharch

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var authenticate: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        authenticate = FirebaseAuth.getInstance()
        val loginBt = findViewById<Button>(R.id.loginBtSignUpScreen)
        val edusername = findViewById<TextInputEditText>(R.id.EdittextUserName)
        val edemail = findViewById<TextInputEditText>(R.id.signupemail)
        val edpassword = findViewById<TextInputEditText>(R.id.signuppassword)
        val signupbt = findViewById<Button>(R.id.SignUpScreenSignUpBt)
        val lottie=findViewById<LottieAnimationView>(R.id.lottieloading)


        loginBt.setOnClickListener {
            startActivity(Intent(this, LoginIn::class.java))
            finish()
        }

        signupbt.setOnClickListener {
            val username = edusername.text.toString()
            val email = edemail.text.toString()
            val password = edpassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "None of the Fields can be Empty! ", Toast.LENGTH_SHORT).show()
            } else if (password.length < 8) {
                Toast.makeText(this, "Password must be  atleast 8 Digits", Toast.LENGTH_SHORT)
                    .show()
            } else {
                 authenticate.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user=Users(username,email)
                                val dialogue=Dialog(this)
                                dialogue.setContentView(R.layout.dialog)
                                dialogue.setCancelable(false)
                                dialogue.show()
                                database=FirebaseDatabase.getInstance().getReference("Users")
                                database.child(username).setValue(user).addOnCompleteListener(this){
                                       dbtask->
                                       if(dbtask.isSuccessful){


                                            Toast.makeText(this@SignUp,"Registration Successfully",Toast.LENGTH_SHORT).show()
                                            dialogue.show()
                                            startActivity(Intent(this,LoginIn::class.java))
                                            finish()
                                       }else{
                                           Toast.makeText(this,"${dbtask.exception?.message}",Toast.LENGTH_SHORT).show()
                                       }
                                   }

                            } else {
                                Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

            }







        }

    }










        override fun onBackPressed() {
            super.onBackPressed()
            startActivity(Intent(this, WelcomeScreen::class.java))
            finishAffinity()
        }
    }
