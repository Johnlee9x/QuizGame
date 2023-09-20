package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quizgame.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.btSignoutHomepage.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@HomePageActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btStartQuizHomepage.setOnClickListener {
            val intent = Intent(this@HomePageActivity, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}