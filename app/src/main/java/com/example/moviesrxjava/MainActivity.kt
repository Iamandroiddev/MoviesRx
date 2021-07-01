package com.example.moviesrxjava

import android.os.Bundle
import android.view.Menu
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.moviesrxjava.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        navController =navHostFragment.navController
        binding.smoothBar.onItemSelected = {i->
            when (i) {
                0 -> {
                    navController!!.navigate(R.id.home)
                }
                1 -> {
                    navController!!.navigate(R.id.wishlist)
                }
                2 -> {
                    navController!!.navigate(R.id.searchMovies)
                }
            }

        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        val navController = Navigation.findNavController(this, R.id.fragment)
        binding.smoothBar.setupWithNavController(menu!!, navController)
        return true
    }
}