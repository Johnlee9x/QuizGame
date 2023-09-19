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
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: SigninActivityBinding
    lateinit var activityResult: ActivityResultLauncher<Intent>
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeTextGg()
        registerActivityForGoogleSignIn()
        binding.btSignin.setOnClickListener {

            val email = binding.edtEmailInputSignin.text.toString()
            val password = binding.edtSigninPasswordInput.text.toString()
            signInWithAccount(email, password)

        }

        binding.signInButton.setOnClickListener{
            sighInWithGoogleAc()
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
                val intent: Intent = Intent(this@SignInActivity, HomePageActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this@SignInActivity, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun sighInWithGoogleAc(){
        val googleSignIn  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("170728334086-lcmhb51ehk0e345d6jgdn2903e4vhnq3.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignIn)

        signIn()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        activityResult.launch(signInIntent)
    }

    private fun changeTextGg(){
        val txtGg = binding.signInButton.getChildAt(0) as TextView
        txtGg.text = "Continue with google"
        txtGg.textSize = 16F
        txtGg.setTextColor(Color.BLACK)

    }

    private fun registerActivityForGoogleSignIn(){
        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == RESULT_OK && data != null) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                firebasSignInWithGg(task)
            }
        }

    }

    private fun firebasSignInWithGg(task: Task<GoogleSignInAccount>) {
        val account = task.getResult(ApiException::class.java)
        Toast.makeText(applicationContext,"Welcome to Quiz Game", Toast.LENGTH_SHORT ).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        firebaseGoogleAccount(account)
    }

    private fun firebaseGoogleAccount(account: GoogleSignInAccount?) {
        val authCredntial = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(authCredntial).addOnCompleteListener { task ->
            if(task.isSuccessful){
            }
        }
    }


}