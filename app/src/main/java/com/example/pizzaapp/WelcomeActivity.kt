package com.example.pizzaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance button next
        val btnNext:Button = findViewById(R.id.buttonNext)

        //event button next click
        btnNext.setOnClickListener{
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }

    }
}