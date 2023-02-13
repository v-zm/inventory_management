package com.vivek.inventorymanagement

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater

// TODO::
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_bar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}