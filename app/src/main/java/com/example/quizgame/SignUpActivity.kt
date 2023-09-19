package com.example.quizgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quizgame.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.buttonsignup.setOnClickListener {
            val email = binding.txtInputemailsignup.text.toString()
            val password = binding.txtInputpasswordsignup.text.toString()

            signUpWithFirebase(email, password)

            /*create user account*/
        }
    }
    fun signUpWithFirebase(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(this@SignUpActivity, "create successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this@SignUpActivity, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }



}