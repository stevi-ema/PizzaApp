package com.example.pizzaapp

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaapp.model.MenuModel

class MakananAdapter(private val list: ArrayList<MenuModel>): RecyclerView.Adapter<MakananAdapter.MakananViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananAdapter.MakananViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview_makanan,
                        parent, false)

        return MakananViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return  list.size
    }


    inner class MakananViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val textId : TextView
        val textNama: TextView
        val textHarga : TextView
        val imageMenu : ImageView
        val buttonEdit : Button
        val buttonDelete : Button
        val context = v.context

        init {
            textId = v.findViewById(R.id.textIdMakanan)
            textNama = v.findViewById(R.id.textNamaMakanan)
            textHarga = v.findViewById(R.id.textHargaMakanan)
            imageMenu = v.findViewById(R.id.imageMakanan)
            buttonEdit = v.findViewById(R.id.buttonEditMakanan)
            buttonDelete = v.findViewById(R.id.buttonHapusMakanan)

            buttonEdit.setOnClickListener {
                EditMenuActivity.idMakanan = textId.text.toString().toInt()
                Toast.makeText(context, EditMenuActivity.idMakanan.toString(), Toast.LENGTH_SHORT).show()
                EditMenuActivity.namaMakanan = textNama.text.toString()
                EditMenuActivity.hargaMakanan = textHarga.text.toString().toInt()
                //gambar
                EditMenuActivity.gambarMakanan = imageMenu.drawable.toBitmap(150,150,null)

                val editIntent = Intent(context,EditMenuActivity::class.java)
                context.startActivity(editIntent)
            }

            buttonDelete.setOnClickListener {
                //object class databaseHelper
                val databaseHelper = DatabaseHelper(context)
                databaseHelper.deleteMenu(textId.text.toString().toInt())
                val intent = Intent(context,MainActivity::class.java)
                context.startActivity(intent)
                /*
                AlertDialog.Builder(context)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure delete this data ?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        Toast.makeText(context, "memilih tombol Ya", Toast.LENGTH_SHORT).show()
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        Toast.makeText(context, "memilih tombol No", Toast.LENGTH_SHORT).show()
                    })
                    .show()
                 */
            }
        }

        fun bind(data: MenuModel){
            val id:Int = data.id
            val nama:String = data.name
            val harga:Int = data.price
            val gambar:Bitmap = data.image

            textId.text = id.toString()
            textNama.text = nama
            textHarga.text = harga.toString()
            imageMenu.setImageBitmap(gambar)
        }
    }
}