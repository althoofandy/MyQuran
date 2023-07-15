package com.example.myquran

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myquran.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.background = null

        val navView: BottomNavigationView = binding.bottomNav
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        binding.apply {
            fabSurah.setOnClickListener {
                navController.navigate(R.id.navigation_surah)
            }
        }
    }
}