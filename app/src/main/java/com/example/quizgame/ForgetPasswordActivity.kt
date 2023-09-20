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
        binding.btFgotPassword.setOnClickListener {
            val email = binding.edtInputFgotpass.text.toString()
            getPassword(email)
        }

    }

    private fun getPassword(email: String) {
        if(email.isBlank() || !email.endsWith("@gmail.com", false)){
            Toast.makeText(this@ForgetPasswordActivity, "Email has to be ending with @gmail.com", Toast.LENGTH_SHORT).show()
        }
        else{
            authen.sendPasswordResetEmail(email).addOnCompleteListener {task ->
                if(task.isSuccessful){
                    Toast.makeText(this@ForgetPasswordActivity, "Password was sent to your email", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,"Don't reset successfully ", Toast.LENGTH_SHORT ).show()
                }
            }
        }
    }

}