package com.ecorner

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecorner.fragments.DashboardFragment
import com.ecorner.fragments.SettingsFragment
import com.ecorner.fragments.infoFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)

        val recView = findViewById<RecyclerView>(R.id.recyclerView)
        recView.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        recView.adapter = adapter

        val icn_cart = findViewById<ImageView>(R.id.ic_cart)
        val icn_acc = findViewById<ImageView>(R.id.ic_account)
        val txt_toolbar = findViewById<TextView>(R.id.toolbar_text);

        icn_cart.setOnClickListener {
            val i = Intent(this@MainActivity, CartActivity::class.java)

            startActivity(i)
        }
        icn_acc.setOnClickListener {

            val i = Intent(this@MainActivity, userProfileActivity::class.java)
            startActivity(i)
        }

        val sharedPrefrences = getSharedPreferences(constants.ECORNER_PREFRENCES, MODE_PRIVATE)
        val username = sharedPrefrences.getString(constants.LOGGED_IN_USER, "")!!
        /*val edit_userName = findViewById<TextView>(R.id.tv_user_id);*/
        /*edit_userName.text= "Hello: ${username}."*/
        val btn_logout = findViewById<TextView>(R.id.button_logout);



        btn_logout.setOnClickListener {
            finish()
            val i = Intent(this@MainActivity, loginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            //i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

    }
}