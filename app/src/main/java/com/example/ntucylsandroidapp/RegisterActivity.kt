package com.example.ntucylsandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //firebase auth
        auth = Firebase.auth

        //make textview scrollable
        registerWarningarningView.movementMethod = ScrollingMovementMethod()
        
        // go back button
        goBackToSignInButton.setOnClickListener { 
            startActivity(Intent(this, SigninActivity::class.java))
        }

        registerButton.setOnClickListener{
            // only allow NTU student
            var account = ""
            var password = ""
            if(accountField.text != null && accountField.text.toString() != ""){
                account = accountField.text.toString().toLowerCase()
            }
            if(passwordField.text != null && passwordField.text.toString() != ""){
                password = passwordField.text.toString()
            }
            val email = "$account@ntu.edu.tw"

            //*********begin of check account and password********
            if(account == "" || password == ""){
                println("niilinlinlnlinlin")
            }else if(account.length != 9 || account.contains("@")){
                showAlert("Error-ID : ", "Seems like you are not a NTU student.")
            }else if(passwordContainSpecialChar(password)){
                showAlert("Error-Special Character detected : ", "Password should not contain " +
                        "`, ~, !, @, #, \$, %, ^, &, *, (, ), _, -, +, =, {, }, [, ], \\, |, :, ;, <, ,, >, ., ?, /, \", '")
            }else if(password.length < 8 || password.length > 20){
                showAlert("Error-Wrong password length : ", "Password length should be >=8 or <=20")
            }else if(!warningCheckBox.isChecked){
                showAlert("Error-Checkbox unchecked : ", "Please read the instruction and check the check-box")
                //*********end of check account and password********
            }else{
                registerProgressBar.alpha = 1F // progress bar start
                // ************ start adding account ***********
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("REGISTER", "createUserWithEmail:success")
                            val user = auth.currentUser
                            startActivity(Intent(this, SigninActivity::class.java))
                            // Send Verification e-mail
                            user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d("SEND VERIFICATION", "Email sent.")
                                    }
                                }
                            // initialize every document
                            val initProfile = hashMapOf(
                                "name" to "",
                                "grade" to "",
                                "epoch" to "",
                                "home" to "",
                                "count" to "",
                                "group" to ""
                            )
                            db.collection("friends").document(account).set(hashMapOf("init" to "init"))
                            db.collection("profile").document(account).set(initProfile)
                            db.collection("invitation").document(account).set(hashMapOf("init" to "init"))
                            db.collection("friends").document(account).update(hashMapOf<String, Any>("init" to FieldValue.delete()))
                            db.collection("invitation").document(account).update(hashMapOf<String, Any>("init" to FieldValue.delete()))

                            registerProgressBar.alpha = 0F
                            val toast = Toast.makeText(this, "Verification mail sent, please check your" +
                                    "NTU mail.", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER,0,0)
                            toast.show()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("REGISTER", "createUserWithEmail:failure", task.exception)
                            showAlert("Error-Authentication failed : ", "Something wrong happened when registering.")
                            registerProgressBar.alpha = 0F
                        }
                    }
            }
            // ************ start adding account ***********
        }

    }

    private fun passwordContainSpecialChar(password: String): Boolean{
        val special = "` ~ ! @ # $ % ^ & * ( ) _ - + = { } [ ] | : ; < , > . ? / '".split(" ")
        for (element in special){
            if(password.contains(element)){
                return true
            }
        }
        return false
    }

    private fun showAlert(title: String, message: String){
        val messageToast = Toast.makeText(this, "$title : $message", Toast.LENGTH_LONG)
        messageToast.setGravity(Gravity.TOP,0,0)
        messageToast.show()
    }

    private fun searchByName(){

    }
}