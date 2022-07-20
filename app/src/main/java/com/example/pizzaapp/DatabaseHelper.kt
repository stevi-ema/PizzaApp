package com.example.pizzaapp

import android.R.id
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import com.example.pizzaapp.model.MenuModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DatabaseHelper(var context:Context): SQLiteOpenHelper(
    context,DATABASE_NAME,null,DATABASE_VERSION
) {

    companion object{
        private val DATABASE_NAME = "pizza"
        private val DATABASE_VERSION = 5
        //table account
        private val TABLE_ACCOUNT = "account"
        //column account table
        private val COLUMN_EMAIL = "email"
        private val COLUMN_NAME = "name"
        private val COLUMN_LEVEL = "level"
        private val COLUMN_PASSWORD = "password"

        //table menu
        private val TABLE_MENU = "menu"
        //column menu table
        private val COLUMN_ID_MENU = "idMenu"
        private val COLUMN_NAMA_MENU = "menuName"
        private val COLUMN_PRICE_MENU = "price"
        private val COLUMN_IMAGE = "photo"

        //table transaksi
        private val TABLE_TRANS = "transaksi"
        //column tabel transaksi
        private val COLUMN_ID_TRANS = "idTransaksi"
        private val COLUMN_TGL = "tanggal"
        private val COLUMN_USER = "user"

        //table detail transaksi
        private val TABLE_DET_TRANSACTION = "detailTrans"
        //column table detail trans
        private val COLUMN_ID_DET_TRX = "idDetailTrx"
        private val COLUMN_ID_TRX = "idTransaksi"
        private val COLUMN_ID_PESAN = "idMenu"
        private val COLUMN_HARGA_PESAN = "harga"
        private val COLUMN_JUMLAH = "jumlah"
    }

    //create table account sql query
    private val CREATE_ACCOUNT_TABLE = ("CREATE TABLE " + TABLE_ACCOUNT + "("
        + COLUMN_EMAIL + " TEXT PRIMARY KEY, "+ COLUMN_NAME +" TEXT, "
        + COLUMN_LEVEL + " TEXT, "+ COLUMN_PASSWORD +" TEXT)")

    private val INSERT_ACCOUNT_TABLE = ("INSERT INTO " + TABLE_ACCOUNT + " VALUES ('stevi.ema@amikom.ac.id','Stevi Ema Wijayanti','Cashier','12345')")

    //drop table account sql query
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_ACCOUNT"

    //create table menu sql query
    private val CREATE_MENU_TABLE = ("CREATE TABLE " + TABLE_MENU + "("
            + COLUMN_ID_MENU + " INT PRIMARY KEY, "+ COLUMN_NAMA_MENU +" TEXT, "
            + COLUMN_PRICE_MENU + " INT, "+ COLUMN_IMAGE +" BLOB)")

    //drop table menu sql query
    private val DROP_MENU_TABLE = "DROP TABLE IF EXISTS $TABLE_MENU"

    //create table transaksi sql query
    private val CREATE_TRANSACTION_TABLE = ("CREATE TABLE " + TABLE_TRANS + "("
            + COLUMN_ID_TRANS + " INT PRIMARY KEY, "+ COLUMN_TGL +" TEXT, "
            + COLUMN_USER + " TEXT)")

    //drop table transaksi sql query
    private val DROP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS $TABLE_TRANS"

    //create table detail transaksi sql query
    private val CREATE_DET_TRANS_TABLE = ("CREATE TABLE " + TABLE_DET_TRANSACTION + "("
            + COLUMN_ID_DET_TRX + " INT PRIMARY KEY, "+ COLUMN_ID_TRX +" INT, "
            + COLUMN_ID_PESAN + " INT, "+ COLUMN_HARGA_PESAN + " INT, "
            + COLUMN_JUMLAH + " INT)")

    //drop table detail transaksi sql query
    private val DROP_DET_TRANS_TABLE = "DROP TABLE IF EXISTS $TABLE_DET_TRANSACTION"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_ACCOUNT_TABLE)
        p0?.execSQL(CREATE_MENU_TABLE)
        p0?.execSQL(CREATE_TRANSACTION_TABLE)
        p0?.execSQL(CREATE_DET_TRANS_TABLE)
        p0?.execSQL(INSERT_ACCOUNT_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DROP_ACCOUNT_TABLE)
        p0?.execSQL(DROP_MENU_TABLE)
        p0?.execSQL(DROP_TRANSACTION_TABLE)
        p0?.execSQL(DROP_DET_TRANS_TABLE)
        onCreate(p0)
    }

    //add new menu
    fun addMenu(menu:MenuModel){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_MENU, menu.id)
        values.put(COLUMN_NAMA_MENU, menu.name)
        values.put(COLUMN_PRICE_MENU, menu.price)
        //prepare image
        val byteOutputStream = ByteArrayOutputStream()
        val imageInByte:ByteArray
        menu.image.compress(Bitmap.CompressFormat.JPEG,100,byteOutputStream)
        imageInByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE, imageInByte)

        val result = db.insert(TABLE_MENU,null, values)
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "Add menu Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Add menu Success",Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    //update new menu
    fun updateMenu(menu:MenuModel){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_MENU, menu.id)
        values.put(COLUMN_NAMA_MENU, menu.name)
        values.put(COLUMN_PRICE_MENU, menu.price)
        //prepare image
        val byteOutputStream = ByteArrayOutputStream()
        val imageInByte:ByteArray
        menu.image.compress(Bitmap.CompressFormat.JPEG,100,byteOutputStream)
        imageInByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE, imageInByte)

        val result = db.update(TABLE_MENU,values, COLUMN_ID_MENU + " = ? " , arrayOf(menu.id.toString())).toLong()
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "Update menu Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Update menu Success",Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    //delete new menu
    fun deleteMenu(id : Int){
        val db = this.writableDatabase

        val result = db.delete(TABLE_MENU, COLUMN_ID_MENU + " = ? " , arrayOf(id.toString())).toLong()
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "Delete menu Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Delete menu Success",Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    //show menu
    @SuppressLint("Range")
    fun showMenu():ArrayList<MenuModel>{
        val listModel = ArrayList<MenuModel>()
        val db = this.readableDatabase
        var cursor:Cursor?=null
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_MENU,null)
        }catch (se:SQLiteException){
            db.execSQL(CREATE_MENU_TABLE)
            return ArrayList()
        }

        var id:Int
        var name:String
        var price:Int
        var imageArray:ByteArray
        var imageBmp:Bitmap

        if(cursor.moveToFirst()) {
            do {
                //get data text
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_MENU))
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAMA_MENU))
                price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE_MENU))
                //get data image
                imageArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
                //convert ByteArray to Bitmap
                val byteInputStream = ByteArrayInputStream(imageArray)
                imageBmp = BitmapFactory.decodeStream(byteInputStream)
                val model = MenuModel(id = id, name = name, price = price, image = imageBmp)
                listModel.add(model)
            } while (cursor.moveToNext())
        }
        return listModel
    }

    @SuppressLint("Range")
    fun checkData(email:String):String{
        val colums = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        var name:String = ""

        val cursor = db.query(TABLE_ACCOUNT, //table to query
            colums, //columns to return
            selection, //columns for WHERE clause
            selectionArgs, //the values for the WHERE clause
            null, //group the rows
            null, //filter by row groups
            null) //the sort order
        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        }

        cursor.close()
        return name
    }

    //add User
    fun addAccount(email:String, name:String, level:String, password:String){
        val db = this.writableDatabase

        //cek data apakah email telah terdaftar
        val data = checkData(email)
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show()

        if(data != ""){
            Toast.makeText(context, "Your email is already registered", Toast.LENGTH_SHORT).show()
        }else{
            val values = ContentValues()
            values.put(COLUMN_EMAIL, email)
            values.put(COLUMN_NAME, name)
            values.put(COLUMN_LEVEL, level)
            values.put(COLUMN_PASSWORD, password)

            val result = db.insert(TABLE_ACCOUNT,null, values)
            //show message
            if (result==(0).toLong()){
                Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Register Success, " +
                        "please login using your new account", Toast.LENGTH_SHORT).show()
            }
        }
        db.close()
    }

    //update user account
    fun updateAccount(email:String, name:String, level:String, password:String){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_LEVEL, level)
        values.put(COLUMN_PASSWORD, password)

        val result = db.update(TABLE_ACCOUNT,values, COLUMN_EMAIL + " = ? " , arrayOf(email)).toLong()
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "Update data Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Update data Success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    //login check
    @SuppressLint("Range")
    fun checkLogin(email:String, password:String):Boolean{
        val colums = arrayOf(COLUMN_EMAIL, COLUMN_NAME, COLUMN_LEVEL, COLUMN_PASSWORD)
        val db = this.readableDatabase
        //selection criteria
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        //selection arguments
        val selectionArgs = arrayOf(email,password)

        val cursor = db.query(TABLE_ACCOUNT, //table to query
            colums, //columns to return
            selection, //columns for WHERE clause
            selectionArgs, //the values for the WHERE clause
            null, //group the rows
            null, //filter by row groups
            null) //the sort order

        val cursorCount = cursor.count
        val result : Boolean

        //check data available or not
        if(cursorCount > 0){
            result = true
            //set data
            if(cursor.moveToFirst()){
                FragmentProfile.email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                FragmentProfile.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                FragmentProfile.level = cursor.getString(cursor.getColumnIndex(COLUMN_LEVEL))
                FragmentProfile.password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            }
        }
        else {
            result = false
        }
        cursor.close()
        db.close()
        return result
    }

    //add new transaksi
    @SuppressLint("Range")
    fun addTransaction(){
        val dbInsert = this.writableDatabase
        val dbSelect = this.readableDatabase
        //declare var
        var lastIdTrans = 0
        var lastIdDetail = 0
        var newIdTrans = 0
        var newIdDetail = 0
        val values = ContentValues()
        //get last idTransaksi
        val cursorTrans: Cursor = dbSelect.rawQuery(
            "SELECT  * FROM $TABLE_TRANS", null)

        val cursorDetail: Cursor = dbSelect.rawQuery(
            "SELECT *  FROM $TABLE_DET_TRANSACTION", null)

        if (cursorTrans.moveToLast()) {
            lastIdTrans = cursorTrans.getInt(0) //to get id, 0 is the column index
        }

        if (cursorDetail.moveToLast()) {
            lastIdDetail = cursorDetail.getInt(0) //to get id, 0 is the column index
        }


        //set data
        newIdTrans = lastIdTrans + 1
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val tanggal = sdf.format(Date())
        val username = FragmentProfile.email
        //insert data transaksi
        values.put(COLUMN_ID_TRANS, newIdTrans)
        values.put(COLUMN_TGL, tanggal)
        values.put(COLUMN_USER, username)
        val result = dbInsert.insert(TABLE_TRANS,null, values)
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "Add transaction Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Add transaction Success",Toast.LENGTH_SHORT).show()
        }

        newIdDetail = lastIdDetail + 1
        var i = 0
        val values2 = ContentValues()
        while(i < TransaksiAdapter.listId.count()){
            values2.put(COLUMN_ID_DET_TRX, newIdDetail)
            values2.put(COLUMN_ID_TRX, newIdTrans)
            values2.put(COLUMN_ID_PESAN, TransaksiAdapter.listId[i])
            values2.put(COLUMN_HARGA_PESAN, TransaksiAdapter.listHarga[i])
            values2.put(COLUMN_JUMLAH, TransaksiAdapter.listJumlah[i])
            val result2 = dbInsert.insert(TABLE_DET_TRANSACTION,null, values2)
            //show message
            if (result2==(0).toLong()){
                Toast.makeText(context, "Add detail Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Add detail Success",Toast.LENGTH_SHORT).show()
            }
            newIdDetail += 1
            i+=1
        }
        dbSelect.close()
        dbInsert.close()
    }
}