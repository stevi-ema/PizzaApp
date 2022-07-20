package com.example.pizzaapp

import android.graphics.Bitmap
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaapp.model.MenuModel


class MenuAdapter(private val list: ArrayList<MenuModel>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview_menu, parent, false)

        return MenuViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        holder.bind(list[position])

    }

    inner class MenuViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val textId : TextView
        val textNama: TextView
        val textHarga : TextView
        val imageMenu : ImageView
        val buttonShop : Button
        val context = v.context

        init {
            textId = v.findViewById(R.id.textIdMenu)
            textNama = v.findViewById(R.id.textNamaMenu)
            textHarga = v.findViewById(R.id.textHargaMenu)
            imageMenu = v.findViewById(R.id.imageMenu)
            buttonShop = v.findViewById(R.id.buttonTambah)

            buttonShop.setOnClickListener {
                TransaksiAdapter.jumlah = TransaksiAdapter.listId.count()

                val jumlahData = TransaksiAdapter.jumlah
                if(jumlahData == 0){
                    TransaksiAdapter.listId += textId.text.toString().toInt()
                    TransaksiAdapter.listNama += textNama.text.toString()
                    TransaksiAdapter.listHarga += textHarga.text.toString().toInt()
                    TransaksiAdapter.listFoto += imageMenu.drawable.toBitmap(80,80,null)
                    TransaksiAdapter.listJumlah += 1
                    TransaksiAdapter.harga = TransaksiAdapter.harga + textHarga.text.toString().toInt()
                    Toast.makeText(v.context,"Purchase Order Success", Toast.LENGTH_SHORT).show()
                }else{
                    //cek data
                    val cek = TransaksiAdapter.listId.find { data -> textId.text.toString().toInt().equals(data) }

                    if(cek == null){
                        TransaksiAdapter.listId += textId.text.toString().toInt()
                        TransaksiAdapter.listNama += textNama.text.toString()
                        TransaksiAdapter.listHarga += textHarga.text.toString().toInt()
                        TransaksiAdapter.listFoto += imageMenu.drawable.toBitmap(80,80,null)
                        TransaksiAdapter.listJumlah += 1
                        TransaksiAdapter.harga = TransaksiAdapter.harga + textHarga.text.toString().toInt()
                        Toast.makeText(v.context,"Purchase Order Success", Toast.LENGTH_SHORT).show()
                    }else{
                        var indek : Int = TransaksiAdapter.listId.indexOf(textId.text.toString().toInt())
                        val qty:Int = TransaksiAdapter.listJumlah[indek] + 1
                        TransaksiAdapter.listJumlah.set(indek,qty)
                        TransaksiAdapter.harga = TransaksiAdapter.harga + TransaksiAdapter.listHarga[indek]
                        Toast.makeText(v.context,"Purchase Order Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        fun bind(data: MenuModel){
            val id:Int = data.id
            val nama:String = data.name
            val harga:Int = data.price
            val gambar: Bitmap = data.image

            textId.text = id.toString()
            textNama.text = nama
            textHarga.text = harga.toString()
            imageMenu.setImageBitmap(gambar)
        }
    }


}