package com.vivek.inventorymanagement.features.inventory.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
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

    //    private val navController by lazy {
//        findNavController(R.id.nav_host_fragment_container1)
//    }
    private val mActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController


        navController.setGraph(R.navigation.nav_graph)
//        val greetingTextObserver = Observer<String> { newGreeting ->
//            mBinding.greetingText.text = newGreeting
//        }
//
//        val buttonTextObserver = Observer<String> { newButtonText ->
//            mBinding.materialButton.text = newButtonText
//        }
//
//        mActivityViewModel.currentGreetingText.observe(this, greetingTextObserver)
//        mActivityViewModel.buttonText.observe(this, buttonTextObserver)
//
//        mBinding.materialButton.setOnClickListener {
//            mActivityViewModel.updateGreetingText("Good Morning")
//            mActivityViewModel.updateButtonText("Click again!!")
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_bar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}