package com.vivek.inventorymanagement

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vivek.inventorymanagement.data.MainActivityViewModel
import com.vivek.inventorymanagement.databinding.ActivityMainBinding

// TODO:: Add UI elements for AppBar
// TODO:: Add page view
// TODO:: Add page UI for list item view
// TODO:: Add page UI for grid item view
// TODO:: implement API
// TODO:: Add Search
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val model: MainActivityViewModel by viewModels()

        val greetingTextObserver = Observer<String> { newGreeting ->
            binding.greetingText.text = newGreeting
        }

        val buttonTextObserver = Observer<String> { newButtonText ->
            binding.materialButton.text = newButtonText
        }

        model.currentGreetingText.observe(this, greetingTextObserver)
        model.buttonText.observe(this, buttonTextObserver)

        binding.materialButton.setOnClickListener {
            model.updateGreetingText("Good Morning")
            model.updateButtonText("Click again!!")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_bar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}