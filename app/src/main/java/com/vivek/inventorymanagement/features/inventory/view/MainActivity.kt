package com.vivek.inventorymanagement.features.inventory.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.databinding.ActivityMainBinding
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel

// TODO:: Add UI elements for AppBar
// TODO:: Add page view
// TODO:: Add page UI for list item view
// TODO:: Add page UI for grid item view
// TODO:: implement API
// TODO:: Add Search

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(mBinding.homeBottomNavigationBar, navController = navController)
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        mActivityViewModel.getData()
    }

}
