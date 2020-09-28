package com.example.ntucylsandroidapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // Fill up account text field if had logged in before
        loadData()

        // Firebase Auth
        auth = Firebase.auth

        // If register button clicked
        gotoRegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Sign in
        loginButton.setOnClickListener {
            //******** Start of checking whether the user is NTU student ********
            var account = ""
            var password = ""
            if(accountText.text != null && accountText.text.toString() != ""){
                account = accountText.text.toString()
            }
            if(passwordText.text != null && passwordText.text.toString() != ""){
                password = passwordText.text.toString()
            }

            var email: String
            if(!account.contains("@")){
                email = account.toLowerCase() + "@ntu.edu.tw"
            }else{
                email = account
            }
            //******** End of checking whether the user is NTU student ********
            if(account != "" && password != ""){
                signinProgressBar.alpha = 1F
                //******** Start of sign in with email ********
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LOGIN", "signInWithEmail:success")
                            val user = auth.currentUser
                            if (user != null) {
                                if(user.isEmailVerified){
                                    // Show success message
                                    val toast = Toast.makeText(this, "Successfully signed in", Toast.LENGTH_LONG)
                                    toast.setGravity(Gravity.CENTER,0,0)
                                    toast.show()
                                    // save the account locally
                                    saveData()
                                    startActivity(Intent(this, MainActivity::class.java))
                                }else{
                                    user.sendEmailVerification()
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d("SEND VERIFICATION", "Email sent.")
                                                // Show send message
                                                val toast = Toast.makeText(this, "Resend a new verification email",
                                                    Toast.LENGTH_LONG)
                                                toast.setGravity(Gravity.CENTER,0,0)
                                                toast.show()
                                            }
                                        }
                                }
                            }
                            // turn of progress bar
                            signinProgressBar.alpha = 0F
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LOGIN", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            // turn of progress bar
                            signinProgressBar.alpha = 0F
                        }

                    }
                //******** End of sign in with email ********
            }
        }
    }

    private  fun saveData(){
        val sharePreference = getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val email = accountText.text.toString()
        var account = ""
        if(email.contains("@")){
            account = email.substring(0,email.indexOf("@"))
        }else{
            account = email.toLowerCase()
        }
        db.collection("profile").document(account).get().addOnSuccessListener { document ->
            sharePreference.edit().apply {
                putString("Name", document.data?.get("name") as String?)
                putString("Grade", document.data?.get("grade") as String?)
                putString("Home", document.data?.get("home") as String?)
                putString("Epoch", document.data?.get("epoch") as String?)
                putString("Group", document.data?.get("group") as String?)
            }.apply()
        }
        sharePreference.edit().apply {
            putString("Email", email)
            putString("Account", account)
        }.apply()
    }

    private fun loadData(){
        val sharePreference = getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val email = sharePreference.getString("Email", "")
        accountText.setText(email)
    }
}