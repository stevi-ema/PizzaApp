package com.example.pizzaapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.pizzaapp.model.MenuModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class AddMenuActivity : AppCompatActivity() {
    lateinit var image : ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance
        image = findViewById(R.id.imageMenu)
        val textId : EditText = findViewById(R.id.menuId)
        val textName : EditText = findViewById(R.id.menuName)
        val textPrice : EditText = findViewById(R.id.menuPrice)
        val btnAddImage: Button = findViewById(R.id.buttonAddImage)


        //event saat buttom add (+) di-klik
        btnAddImage.setOnClickListener {
            pickImageGalery()
        }

        //instance
        val btnSaveMenu: Button = findViewById(R.id.buttonSaveMenu)
        //event saat button save di klik
        btnSaveMenu.setOnClickListener {
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val id : Int = textId.text.toString().toInt()
            val name : String = textName.text.toString().trim()
            val price : Int = textPrice.text.toString().toInt()
            val bitmapDrawable : BitmapDrawable = image.drawable as BitmapDrawable
            val bitmap : Bitmap = bitmapDrawable.bitmap

            val menuModel = MenuModel(id,name,price,bitmap)
            databaseHelper.addMenu(menuModel)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pickImageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            image.setImageURI(data?.data)
        }
    }
}