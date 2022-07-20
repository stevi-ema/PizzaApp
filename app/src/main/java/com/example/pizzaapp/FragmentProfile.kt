package com.example.pizzaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentProfile : Fragment() {
    //declare var

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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //instance
        val textEmail:TextView = view.findViewById(R.id.textEmailAccount)
        val textNama:EditText = view.findViewById(R.id.editTextPersonName)
        val textLevel:EditText = view.findViewById(R.id.editTextLevel)
        val textPassword:EditText = view.findViewById(R.id.editTextPassword)
        val buttonSave:Button = view.findViewById(R.id.buttonSaveAccount)

        //set data
        textEmail.text = email
        textNama.setText(name)
        textLevel.setText(level)
        textPassword.setText(password)

        buttonSave.setOnClickListener {
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this.requireContext())
            //declare data
            email = textEmail.text.toString().trim()
            name = textNama.text.toString().trim()
            level = textLevel.text.toString().trim()
            password = textPassword.text.toString().trim()

            //insert data
            databaseHelper.updateAccount(email, name, level, password)
        }

        return view
    }

    companion object {
        var email = "tes@gmail.com"
        var name = "Tes nama"
        var level = "tes level"
        var password = "password"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}