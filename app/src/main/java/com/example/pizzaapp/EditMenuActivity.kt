package com.example.pizzaapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.pizzaapp.model.MenuModel

class EditMenuActivity : AppCompatActivity() {


    lateinit var image : ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
        //set variabel untuk menampung data yang terpilih
        var idMakanan = 1
        var namaMakanan = "tes"
        var hargaMakanan = 90000
        //var gambarMakanan = R.drawable.logo_pizza
        lateinit var gambarMakanan : Bitmap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance
        image = findViewById(R.id.editImageMenu)
        val textId : TextView = findViewById(R.id.editId)
        val textName : EditText = findViewById(R.id.editName)
        val textPrice : EditText = findViewById(R.id.editPrice)
        val btnAddImage: Button = findViewById(R.id.buttonEditImage)
        val btnUpdateMakanan : Button = findViewById(R.id.buttonSaveEditMenu)

        //input data
        textId.text = idMakanan.toString()
        textName.setText(namaMakanan)
        textPrice.setText(hargaMakanan.toString())
        image.setImageBitmap(gambarMakanan)

        //event saat buttom add (+) di-klik
        btnAddImage.setOnClickListener {
            pickImageGalery()
        }

        //event saat button save di-klik
        btnUpdateMakanan.setOnClickListener {
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val id : Int = textId.text.toString().toInt()
            val name : String = textName.text.toString().trim()
            val price : Int = textPrice.text.toString().toInt()
            val bitmapDrawable : BitmapDrawable = image.drawable as BitmapDrawable
            val bitmap : Bitmap = bitmapDrawable.bitmap

            val menuModel = MenuModel(id,name,price,bitmap)
            databaseHelper.updateMenu(menuModel)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pickImageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, AddMenuActivity.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == AddMenuActivity.IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            image.setImageURI(data?.data)
        }
    }
}