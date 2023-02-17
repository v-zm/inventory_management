package com.vivek.inventorymanagement.features.inventory.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.databinding.ActivityMainBinding
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO:: Add UI elements for AppBar -> Done
// TODO:: Add page view
// TODO:: Add page UI for list item view -> Done
// TODO:: Add page UI for grid item view -> Done
// TODO:: implement API -> Done
// TODO:: Add Search UI -> Done
// TODO:: implement search functionality

@AndroidEntryPoint
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

    }

    override fun onStart() {
        super.onStart()
        mActivityViewModel.getInventoryProducts()
        observeLoadingState()
        startSearchListener()
        inflateFilterMenu()
        observerSelectedFilterMenuOption()
    }

    private fun observeLoadingState() {
        val loadingObserver = Observer<Boolean> { isLoading ->
            mBinding.isInventoryLoading = isLoading
        }
        mActivityViewModel.isLoading.observe(this, loadingObserver)
    }

    private fun startSearchListener() {
        mBinding.inventorySearchBar.inventorySearchTextField.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, _p1: Int, _p2: Int, _p3: Int) {
                text.let { tempText -> mActivityViewModel.onSearch(tempText.toString()) }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun inflateFilterMenu() {
        val menu = PopupMenu(this, mBinding.inventorySearchBar.filterText)
        menu.inflate(R.menu.inventory_search_options)

        mBinding.inventorySearchBar.selectedOption = menu.menu.getItem(0).title.toString()
        mBinding.inventorySearchBar.filterText.setOnClickListener {
            menu.show()
        }
        menu.setOnMenuItemClickListener { menuItem: MenuItem ->
            if (menuItem.order == 2) {
                menuItem.isChecked = !menuItem.isChecked
                if (menuItem.itemId == R.id.search_include_image_selectable) {
                    mActivityViewModel.searchOnlyWithImage = menuItem.isChecked
                }
                false
            } else {
                mActivityViewModel.inventoryFilterSelectedOption.value = menuItem.title.toString()
                true
            }

        }
    }

    private fun observerSelectedFilterMenuOption() {

        val inventorySelectedOptionObserver = Observer<String> { selectedOption ->
            mBinding.inventorySearchBar.selectedOption = selectedOption
        }
        mActivityViewModel.inventoryFilterSelectedOption.observe(
            this,
            inventorySelectedOptionObserver
        )
    }
}
