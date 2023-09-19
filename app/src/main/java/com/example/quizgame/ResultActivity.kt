package com.example.quizgame

import android.content.Intent
import android.media.MediaDrm.OnEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.quizgame.databinding.ActivityResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResultActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityResultBinding

    private var database = FirebaseDatabase.getInstance()

    private var dataRef =database.reference.child("Scores")
    val authen = FirebaseAuth.getInstance()
    val userId = authen.currentUser!!.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        getResult()

        viewBinding.btExist.setOnClickListener {
            finish()
        }

        viewBinding.btPlayagain.setOnClickListener {
            val intent = Intent(this@ResultActivity, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getResult() {
        dataRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var num_Crt = snapshot.child(userId).child("correct").value.toString()
                    var numWrong = snapshot.child(userId).child("wrong").value.toString()
                    viewBinding.apply {
                        numCrt.text = num_Crt
                        Log.d("numCrt", num_Crt)
                        numWrongans.text = numWrong
                        Log.d("numWrong", numWrong)
                    }
                }
                else{
                    Toast.makeText(this@ResultActivity, "User Not Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}