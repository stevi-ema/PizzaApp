package com.example.pizzaapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTransaction.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTransaction : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        //instance pallete fragment transaksi
        rvTransaksi = view.findViewById(R.id.recyclerTransaksi)
        txtOrder = view.findViewById(R.id.textTotalOrder)
        txtTax = view.findViewById(R.id.textTax)
        txtTotal = view.findViewById(R.id.textTotalPrice)
        buttonPay = view.findViewById(R.id.buttonPayNow)

        //call display data
        displayData()

        buttonPay.setOnClickListener {
            val dbHelper = DatabaseHelper(this.requireContext())
            dbHelper.addTransaction()
            activity?.let {
                val intent = Intent(it, PaymentActivity::class.java)
                it.startActivity(intent)
            }
        }
        return view
    }

    fun displayData(){
        //set layout recycler view
        rvTransaksi.layoutManager = LinearLayoutManager(activity)
        //set adapter recycler view
        rvTransaksi.adapter = TransaksiAdapter()

        txtOrder.text = TransaksiAdapter.harga.toString()
        txtTax.text = (TransaksiAdapter.harga * 0.10).toString()
        txtTotal.text = (TransaksiAdapter.harga + (TransaksiAdapter.harga * 0.10)).toString()
    }

    companion object {
        lateinit var rvTransaksi: RecyclerView
        lateinit var txtOrder: TextView
        lateinit var txtTax: TextView
        lateinit var txtTotal: TextView
        lateinit var buttonPay:Button
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentTransaction.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTransaction().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}