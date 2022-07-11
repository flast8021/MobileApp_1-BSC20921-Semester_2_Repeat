package com.ecorner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class forgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btn_submit = findViewById<TextView>(R.id.button_submit);
        val txt_login_email_forgot = findViewById<TextView>(R.id.et_login_email_forgot);

        btn_submit.setOnClickListener {
            val email: String =txt_login_email_forgot.text.toString().trim{ it <= ' '}
            if (email.isEmpty()){
                Toast.makeText(
                    this@forgotPassword,
                    "Please Input Email Address",
                    Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@forgotPassword,
                                "Reset Link is sent to provided Email Address",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@forgotPassword,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

    }
}