package com.example.quizgame

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.example.quizgame.databinding.SigninActivityBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: SigninActivityBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeTextGg()
        binding.btSignin.setOnClickListener {

            val email = binding.edtEmailInputSignin.text.toString()
            val password = binding.edtSigninPasswordInput.text.toString()
            signInWithAccount(email, password)

        }

        binding.signInButton.setOnClickListener{

        }

        binding.txtSignup.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)

        }

        binding.txtfgotpssw.setOnClickListener {
            val intent = Intent(this@SignInActivity, ForgetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun signInWithAccount(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val intent = Intent(this@SignInActivity, HomePageActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this@SignInActivity, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("170728334086-lcmhb51ehk0e345d6jgdn2903e4vhnq3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }
    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(
                Intent(
                    this, HomePageActivity
                    ::class.java
                )
            )
            finish()
        }
    }
    private fun changeTextGg(){
        val txtGg = binding.signInButton.getChildAt(0) as TextView
        txtGg.text = "Continue with google"
        txtGg.textSize = 16F
        txtGg.setTextColor(Color.BLACK)

    }



}