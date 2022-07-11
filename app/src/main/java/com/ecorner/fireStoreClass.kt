package com.ecorner

import android.app.Activity
import android.app.SharedElementCallback
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
import java.util.*

class fireStoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun getUserDetails(activity: Activity){
        mFireStore.collection(constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())
                val user = document.toObject(user::class.java)!!
                val sharedPrefrences =
                    activity.getSharedPreferences(
                        constants.ECORNER_PREFRENCES,
                        Context.MODE_PRIVATE
                    )
                val editor: SharedPreferences.Editor = sharedPrefrences.edit()
                editor.putString(
                constants.LOGGED_IN_USER,
                "${user.firstName} ${user.lastName}"
                )
                editor.apply()

                when(activity){
                    is loginActivity -> {
                        val user = document.toObject(user::class.java)!!
                        activity.userLoggedInSuccess(user)
                    }
                }

            }
    }
    fun registerUser(activity: RegisterActivity, userInfo: user){
        mFireStore.collection(constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener{
                e ->
                Log.e(activity.javaClass.simpleName,
                    "Error While Registering User",
                    e
                )
            }
    }
    fun updatrProfileData(activity: Activity, userHashMap: HashMap<String, Any>){
        mFireStore.collection(constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {

            }
            .addOnFailureListener{e->
                when(activity){
                    is userProfileActivity ->{
                        activity.userProfileUpdateSucess()
                    }
                }
            }

    }
    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId=""
        if(currentUser != null){
            currentUserId = currentUser.uid
        }
    return currentUserId
    }

}