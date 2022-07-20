package com.example.pizzaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance
        val bottomNav:BottomNavigationView = findViewById(R.id.bottomNavigationView);

        //set fragment
        val accountFragment=FragmentProfile()
        val makananFragment=FragmentMakanan()
        val transactionFragment=com.example.pizzaapp.FragmentTransaction()
        val reportFragment = FragmentReport();
        val menuFragment = FragmentMenu();

        //default fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,menuFragment)
            commit()
        }

        currentFragment(menuFragment)

        //floating bottom menu
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container,menuFragment)
                commit()
            }
        }

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.fragmentProfile->currentFragment(accountFragment)
                R.id.fragmentMakanan->currentFragment(makananFragment)
                R.id.fragmentTransaction->currentFragment(transactionFragment)
                R.id.fragmentReport->currentFragment(reportFragment)

            }
            true
        }
    }

    private fun currentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }
}