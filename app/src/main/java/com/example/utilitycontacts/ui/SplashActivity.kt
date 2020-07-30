package com.example.utilitycontacts.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.utilitycontacts.ui.infoList.InfoViewModel
import com.example.utilitycontacts.R

class SplashActivity : AppCompatActivity() {

    private lateinit var infoViewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        handler.postDelayed({
            infoViewModel.readFirebaseDb()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)


    }
}