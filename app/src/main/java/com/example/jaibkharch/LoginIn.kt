package com.example.jaibkharch

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginIn : AppCompatActivity() {
    private lateinit var authenticate:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_in)
        authenticate=FirebaseAuth.getInstance()



        val loginbt=findViewById<Button>(R.id.LoginBt)
        val edemail=findViewById<TextInputEditText>(R.id.EmailLoginScreen)
        val edpassword=findViewById<TextInputEditText>(R.id.PasswordLoginScreen)
        val resetpasswordbt=findViewById<Button>(R.id.resetpassword)
        val lottie=findViewById<LottieAnimationView>(R.id.lottieloading)

        val signUp=findViewById<Button>(R.id.SignUpBtLoginScreen)

        loginbt.setOnClickListener {
            val email=edemail.text.toString()
            val password=edpassword.text.toString()

            if(email.isEmpty()|| password.isEmpty()){
                Toast.makeText(this,"Enter the Login Details",Toast.LENGTH_SHORT).show()
            }else{
                  val dialogue=Dialog(this)
                  dialogue.setContentView(R.layout.dialog)
                  dialogue.setCancelable(false)
                  dialogue.show()

                     authenticate.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                         if (task.isSuccessful) {
                             dialogue.dismiss()

                             Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show()
                             startActivity(Intent(this, MainActivity::class.java))
                             finish()
                         } else {
                             dialogue.dismiss()
                             Toast.makeText(this, "Invalid Credentials ", Toast.LENGTH_SHORT).show()

                         }

                     }


            }




        }

        resetpasswordbt.setOnClickListener {

            val emailreset=edemail.text.toString()
            if(emailreset.isNotEmpty()){
                authenticate.sendPasswordResetEmail(emailreset).addOnCompleteListener(this){
                        task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Email  Sent Successfully",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"${task.exception?.message}",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Enter the Email!",Toast.LENGTH_SHORT).show()
            }


        }



        signUp.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,WelcomeScreen::class.java))
        finishAffinity()
    }
}