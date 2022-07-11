package com.ecorner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.trimmedLength
import com.ecorner.user
import com.google.firebase.firestore.remote.FirestoreChannel
import java.util.*
import android.widget.CheckBox
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User

class userProfileActivity : AppCompatActivity() {
    private lateinit var mUserDetails: user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val edit_phoneNumber = findViewById<TextView>(R.id.et_phoneNumber);
        val icn_cart = findViewById<ImageView>(R.id.ic_cart)

        icn_cart.setOnClickListener {
            val i = Intent(this@userProfileActivity, CartActivity::class.java)
            startActivity(i)
        }
        val btn_submit = findViewById<TextView>(R.id.botn_submit)
        btn_submit.setOnClickListener {
            val intent =
                Intent(this@userProfileActivity, MainActivity::class.java)

            startActivity(intent)
        }
        if(intent.hasExtra(constants.EXTRA_USER_DETAILS)){
            mUserDetails = intent.getParcelableExtra(constants.EXTRA_USER_DETAILS)!!

            val edit_firstName = findViewById<TextView>(R.id.et_firstName);
            edit_firstName.isEnabled=false
            edit_firstName.setText(mUserDetails.firstName)

            val edit_lastName = findViewById<TextView>(R.id.et_lastName);
            edit_lastName.isEnabled=false
            edit_lastName.setText(mUserDetails.lastName)

            val edit_email = findViewById<TextView>(R.id.et_email_id);
            edit_email.isEnabled=false
            edit_email.setText(mUserDetails.email)




            val btn_submit = findViewById<TextView>(R.id.botn_submit)

            when{
                TextUtils.isEmpty(edit_phoneNumber.text.toString().trim{ it <= ' '}) -> {

                    edit_phoneNumber.isEnabled=true
                    edit_phoneNumber.setText(mUserDetails.mobile)
                }else -> {
                val pass: String = edit_phoneNumber.text.toString().trim { it <= ' ' }
            pass.toString()
                btn_submit.setOnClickListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(
                        this@userProfileActivity,
                        "Details Saved.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                }
            }

while(edit_phoneNumber == null) {

        Toast.makeText(
            this@userProfileActivity,
            "Please Input Phone Number",
            Toast.LENGTH_SHORT
        ).show()


        btn_submit.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

}


        }
    fun onClick(v: View?){
        val edit_phoneNumber = findViewById<TextView>(R.id.et_phoneNumber);
        val rb_male = findViewById<TextView>(R.id.rad_btn_male);
        val rb_female = findViewById<TextView>(R.id.rad_btn_female);
        if(v != null){
            when(v.id){
                R.id.botn_submit -> {
                    if(validateUserProfileDetails()){
                        val userHashMap = HashMap<String, Any>()
                        val mobileNumber = edit_phoneNumber.text.toString().trim{ it <=' '}
                        val gender = if (rb_male.equals(true)){
                            constants.MALE
                        }else{
                            constants.FEMALE
                        }
                        if(edit_phoneNumber==null) {
                            userHashMap[constants.MOBILE] = edit_phoneNumber.toString()
                        }else{

                            userHashMap[constants.MOBILE] = edit_phoneNumber.toString()
                        }

                        userHashMap[constants.GENDER] = gender
                        userHashMap[constants.COMPLETE_PROFILE] = 1
                        Toast.makeText(
                            this@userProfileActivity,
                            "Your details are valid and are accepted.",
                            Toast.LENGTH_SHORT
                        ).show()
                        fireStoreClass().updatrProfileData(this, userHashMap)
                    }
                }
            }
        }
    }
    }
    private fun validateUserProfileDetails(): Boolean{
        val edit_phoneNumber = findViewById<TextView>(R.id.et_phoneNumber);
        return when{
            TextUtils.isEmpty(edit_phoneNumber.text.toString().trim{it <= ' '})->{
                Toast.makeText(
                    this@userProfileActivity,
                    "Please Input Phone Number",
                    Toast.LENGTH_SHORT).show()
                false
            }else -> {
                true
            }
        }
    }
    fun userProfileUpdateSucess(){
        Toast.makeText(
            this@userProfileActivity,
            "Your profile is updated Successfully updated.",
            Toast.LENGTH_SHORT
        ).show()
        startActivity(Intent(this@userProfileActivity,MainActivity::class.java))
        finish()
    }
}
