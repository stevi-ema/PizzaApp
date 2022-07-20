package com.example.pizzaapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMakanan.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMakanan : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_makanan, container, false)
        //instance
        val rvmakanan: RecyclerView = view.findViewById(R.id.recyclerMakanan)
        val databaseHelper = DatabaseHelper(this.requireContext())
        //call function show data menu
        val listData = databaseHelper.showMenu()
        //set layout recycler view
        rvmakanan.layoutManager = LinearLayoutManager(activity)
        //set adapter recycler view
        rvmakanan.adapter = MakananAdapter(listData)

        //instance button add menu
        val buttonAdd : Button = view.findViewById(R.id.buttonAddMenu)
        //event saat button add menu di-klik
        buttonAdd.setOnClickListener {
            requireActivity().run{
                startActivity(Intent(this, AddMenuActivity::class.java))
                finish()
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMakanan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMakanan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}