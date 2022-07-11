package com.ecorner

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User

class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn_textRegister = findViewById<TextView>(R.id.textButton_Register);
        val btn_login = findViewById<Button>(R.id.btn_login);
        val edit_login_email = findViewById<TextView>(R.id.et_login_email);
        val edit_login_password = findViewById<TextView>(R.id.et_login_password);
        val btn_forgetPassword = findViewById<TextView>(R.id.txt_forget_password);

        btn_textRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        btn_forgetPassword.setOnClickListener {
            startActivity(Intent(this,forgotPassword::class.java))
        }

        btn_login.setOnClickListener {
            when{
                TextUtils.isEmpty(edit_login_email.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@loginActivity,
                        "Please Input Email Adress",
                        Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(edit_login_password.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@loginActivity,
                        "Please Input Password",
                        Toast.LENGTH_SHORT).show()
                }else -> {
                val email: String = edit_login_email.text.toString().trim { it <= ' ' }
                val password: String = edit_login_password.text.toString().trim { it <= ' ' }

                //Creating an instance & registering a user with Email & password
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->
                            if (task.isSuccessful) {
//                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                fireStoreClass().getUserDetails(this@loginActivity)
                                Toast.makeText(
                                    this@loginActivity,
                                    "You are Logged in Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@loginActivity, userProfileActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                //finish()
                            } else {
                                Toast.makeText(
                                    this@loginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
            }
            }
        }
/*        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            val i = Intent(this@loginActivity, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out")
        }*/
    }
    fun onClick(view: View?){
        if(view != null){
        when(view.id){
            R.id.txt_forget_password -> {
                val intent = Intent(this@loginActivity, forgotPassword::class.java)
                startActivity(intent)
            }
            }
        }
    }
    fun userLoggedInSuccess(user: user){


        if(user.profileCompleted == 0) {
            val intent =Intent(this@loginActivity, userProfileActivity::class.java)
            intent.putExtra(constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)

        }else{
            startActivity(Intent(this@loginActivity, MainActivity::class.java))
        }
        finish()
    }
}