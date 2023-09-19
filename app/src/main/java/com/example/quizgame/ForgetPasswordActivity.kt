package com.example.quizgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.quizgame.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private var authen = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        getPasswrod()

    }

    private fun getPasswrod() {
        binding.btFgotPassword.setOnClickListener {
            val email = binding.edtInputFgotpass.text.toString()
            authen.sendPasswordResetEmail(email).addOnCompleteListener {task ->
                if(task.isSuccessful){
                    Toast.makeText(this@ForgetPasswordActivity, "Passwors was sent to your email", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,"Don't reset successfully ", Toast.LENGTH_SHORT ).show()
                }
            }
        }
    }

}