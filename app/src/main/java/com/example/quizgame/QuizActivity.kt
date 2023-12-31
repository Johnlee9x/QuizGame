package com.example.quizgame

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.quizgame.databinding.ActivityQuizPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuizActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityQuizPageBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbReference: DatabaseReference = database.reference.child("Questions")
    private lateinit var countDown: CountDownTimer
    var qtNum = 1
    var qttoTal = 0
    private var numWrong = 0
    private var crNum = 0
    var qtion = ""
    private var ansA = ""
    private var ansB = ""
    private var ansC = ""
    private var ansD = ""
    private var crans = ""
    private var userAns = ""
    val authen = FirebaseAuth.getInstance()
    val user = authen.currentUser
    private val scoreDb = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuizPageBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        showQuestion()

        viewBinding.btnNext.setOnClickListener {
            showQuestion()
        }
        viewBinding.txtA.setOnClickListener {
            userAns = "a"
            if(userAns == crans){
                viewBinding.txtA.setBackgroundColor(Color.GREEN)
                crNum++
                viewBinding.txtNumcrt.text = crNum.toString()
            }
            else{
                viewBinding.txtA.setBackgroundColor(Color.RED)
                numWrong++
                viewBinding.txtNumwrong.text = numWrong.toString()
                showAns()
            }
            disableClic()
            stopCntD()

        }
        viewBinding.txtB.setOnClickListener {

            userAns = "b"
            if(userAns == crans){
                viewBinding.txtB.setBackgroundColor(Color.GREEN)
                crNum++
                viewBinding.txtNumcrt.text = crNum.toString()
            }
            else{
                viewBinding.txtB.setBackgroundColor(Color.RED)
                numWrong++
                viewBinding.txtNumwrong.text = numWrong.toString()
                showAns()
            }
            disableClic()
            stopCntD()

        }
        viewBinding.txtC.setOnClickListener {

            userAns = "c"
            if(userAns == crans){

                viewBinding.txtC.setBackgroundColor(Color.GREEN)
                crNum++
                viewBinding.txtNumcrt.text = crNum.toString()
            }
            else{
                viewBinding.txtC.setBackgroundColor(Color.RED)
                numWrong++
                viewBinding.txtNumwrong.text = numWrong.toString()
                showAns()
            }
            disableClic()
            stopCntD()

        }
        viewBinding.txtD.setOnClickListener {
            userAns = "d"
            if(userAns == crans){
                viewBinding.txtD.setBackgroundColor(Color.GREEN)
                crNum++
                viewBinding.txtNumcrt.text = crNum.toString()
            }
            else{
                viewBinding.txtD.setBackgroundColor(Color.RED)
                numWrong++
                viewBinding.txtNumwrong.text = numWrong.toString()
                showAns()
            }
            disableClic()
            stopCntD()
        }
        viewBinding.btnFinish.setOnClickListener {
            getRecord()
        }

    }

    private fun showQuestion(){
        refreshOption()
        dbReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                qttoTal = snapshot.childrenCount.toInt()
                if(qtNum <= qttoTal){
                    qtion = snapshot.child(qtNum.toString()).child("q").value.toString()
                    ansA = snapshot.child(qtNum.toString()).child("a").value.toString()
                    ansB = snapshot.child(qtNum.toString()).child("b").value.toString()
                    ansC = snapshot.child(qtNum.toString()).child("c").value.toString()
                    ansD = snapshot.child(qtNum.toString()).child("d").value.toString()
                    crans = snapshot.child(qtNum.toString()).child("ans").value.toString()

                    viewBinding.txtA.text = ansA
                    viewBinding.txtB.text = ansB
                    viewBinding.txtC.text = ansC
                    viewBinding.txtD.text = ansD
                    viewBinding.txtQuestion.text = qtion
                }
                else{
                    stopCntD()
                    val dialogMsg = AlertDialog.Builder(this@QuizActivity)
                    dialogMsg.setTitle("Quiz Game")
                    dialogMsg.setMessage("Finish the Quiz, see the result")
                    dialogMsg.setCancelable(false)
                    dialogMsg.setPositiveButton("Result"){ _, _
                        -> getRecord()
                    }
                    dialogMsg.setNegativeButton("play again"){_,_
                        -> val intent = Intent(this@QuizActivity, HomePageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    dialogMsg.create().show()
                }
                qtNum++

                viewBinding.progressBar.visibility = View.INVISIBLE
                viewBinding.layoutProgressBar.visibility =View.INVISIBLE
                viewBinding.layoutTime.visibility = View.VISIBLE
                viewBinding.layoutQuestion.visibility = View.VISIBLE
                viewBinding.layoutBtn.visibility = View.VISIBLE

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
    
    private fun showAns(){
        when(crans){
            "a" -> viewBinding.txtA.setBackgroundColor(Color.GREEN)
            "b" -> viewBinding.txtB.setBackgroundColor(Color.GREEN)
            "c" -> viewBinding.txtC.setBackgroundColor(Color.GREEN)
            "d" -> viewBinding.txtD.setBackgroundColor(Color.GREEN)
        }

    }
    fun disableClic(){
        viewBinding.txtA.isClickable = false
        viewBinding.txtB.isClickable = false
        viewBinding.txtC.isClickable = false
        viewBinding.txtD.isClickable = false

    }

    fun refreshOption(){
        startCntD()
        viewBinding.txtA.isClickable = true
        viewBinding.txtB.isClickable = true
        viewBinding.txtC.isClickable = true
        viewBinding.txtD.isClickable = true

        viewBinding.txtA.setBackgroundColor(Color.WHITE)
        viewBinding.txtB.setBackgroundColor(Color.WHITE)
        viewBinding.txtC.setBackgroundColor(Color.WHITE)
        viewBinding.txtD.setBackgroundColor(Color.WHITE)
    }
    private fun startCntD() {
        countDown = object : CountDownTimer(30000, 1000) {
            override fun onTick(tilFn: Long) {
                viewBinding.txtCntdown.text = (tilFn / 1000).toString()
            }

            override fun onFinish() {
                disableClic()
                viewBinding.txtQuestion.text = getString(R.string.time_over)
            }

        }.start()
    }
    private fun stopCntD(){
        countDown.cancel()
    }
    private fun getRecord(){
        user?.let {
            val userId = it.uid
            scoreDb.child("Scores").child(userId).child("correct").setValue(crNum)
            scoreDb.child("Scores").child(userId).child("wrong").setValue(numWrong).addOnSuccessListener {
            }
            val intent = Intent(this@QuizActivity, ResultActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}