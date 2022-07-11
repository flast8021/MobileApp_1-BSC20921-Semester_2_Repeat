package com.ecorner

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.util.NumberUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var mProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn_textLogin = findViewById<TextView>(R.id.textButton);
        val btn_register = findViewById<Button>(R.id.button_register);
        val edit_register_email = findViewById<TextView>(R.id.et_register_email);
        val edit_register_password = findViewById<TextView>(R.id.et_register_password);
        val edit_register_fName = findViewById<TextView>(R.id.et_register_fName);
        val edit_register_lName = findViewById<TextView>(R.id.et_register_lName);
        val edit_register_phone = findViewById<TextView>(R.id.et_phoneNumber);

        btn_textLogin.setOnClickListener {
            startActivity(Intent(this,loginActivity::class.java))
        }

        btn_register.setOnClickListener {
            when{
                TextUtils.isEmpty(edit_register_email.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Input Email Adress",
                        Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(edit_register_password.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Input Password",
                        Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(edit_register_fName.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Input First Name",
                        Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(edit_register_lName.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Input Last Name",
                        Toast.LENGTH_SHORT).show()
                }

                else -> {
                    //showProgressDialog(resources.getString(R.string.pleaseWait))
                val email: String = edit_register_email.text.toString().trim { it <= ' ' }
                val password: String = edit_register_password.text.toString().trim { it <= ' ' }

                //Creating an instance & registering a user with Email & password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->
                            //hideProgressDialog()
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                val user = user(
                                    firebaseUser.uid,
                                    edit_register_fName.text.toString().trim { it <= ' '},
                                    edit_register_lName.text.toString().trim { it <= ' '},
                                    edit_register_email.text.toString().trim { it <= ' '}
                                )
                                fireStoreClass().registerUser(this@RegisterActivity,user)
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "You are Registered Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                FirebaseAuth.getInstance().signOut()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
            }
            }
        }


    }
    fun showProgressDialog(string: String) {
        mProgressDialog.setContentView(R.layout.dialog_progress)
        //mProgressDialog.text = text
        mProgressDialog = Dialog(this)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
    fun userRegistrationSuccess(){
        Toast.makeText(
            this@RegisterActivity,
            "User Registered Sucessfully",
            Toast.LENGTH_SHORT
        ).show()
    }
}