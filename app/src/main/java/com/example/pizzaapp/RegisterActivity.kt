package com.example.pizzaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance text
        val txtEmail: EditText = findViewById(R.id.registerEmail)
        val txtName: EditText = findViewById(R.id.registerPersonName)
        val txtLevel: EditText = findViewById(R.id.registerLevel)
        val txtPassword: EditText = findViewById(R.id.registerPassword)
        //instance button register
        val btnRegister: Button = findViewById(R.id.buttonRegisterAccount)

        //event button save
        btnRegister.setOnClickListener {
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)
            //declare data
            val email:String = txtEmail.text.toString().trim()
            val name:String = txtName.text.toString().trim()
            val level:String = txtLevel.text.toString().trim()
            val password:String = txtPassword.text.toString().trim()

            //insert data
            databaseHelper.addAccount(email,name, level, password)

            //show LoginActivity
            val intentLogin = Intent(this@RegisterActivity,
                LoginActivity::class.java)
            startActivity(intentLogin)


        }
    }
}