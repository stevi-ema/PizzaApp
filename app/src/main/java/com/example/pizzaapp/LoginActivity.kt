package com.example.pizzaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    //declare var -> global

    override fun onCreate(savedInstanceState: Bundle?) {
        //declare var -> lokal
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance text
        val txtUsername:EditText = findViewById(R.id.editTextEmail)
        val txtPassword:EditText = findViewById(R.id.editTextPassword)
        val txtLinkRegister:TextView = findViewById(R.id.textViewRegister)
        //instance button login
        val btnLogin:Button = findViewById(R.id.buttonLogin)

        //event saat textview register di-klik
        txtLinkRegister.setOnClickListener {
            val intentRegister = Intent(this@LoginActivity,
                RegisterActivity::class.java)
            startActivity(intentRegister)
        }

        //event button login
        btnLogin.setOnClickListener {
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val email = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            //check login
            val result:Boolean = databaseHelper.checkLogin(email,password)
            if (result == true){
                Toast.makeText(this@LoginActivity,"Login Success ",
                    Toast.LENGTH_SHORT).show()
                val intentLogin = Intent(this@LoginActivity,
                    MainActivity::class.java)
                startActivity(intentLogin)
            }else{
                Toast.makeText(this@LoginActivity,"Login Failed, Try Again !!!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}