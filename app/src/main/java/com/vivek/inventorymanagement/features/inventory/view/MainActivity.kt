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
import com.vivek.inventorymanagement.features.inventory.model.MenuInventorySearchOptionsOrderInCategory
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO:: Add page view

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mActivityViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpNavigation()
    }

    override fun onStart() {
        super.onStart()
        fetchInventoryProducts();
        observeLoadingState()
        initiateSearchListener()
        inflateFilterMenu()
        observerSelectedFilterMenuOption()
        observeErrorState()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(mBinding.homeBottomNavigationBar, navController = navController)
    }

    private fun fetchInventoryProducts() {
        mActivityViewModel.getInventoryProducts()
    }

    private fun observeLoadingState() {
        val loadingObserver = Observer<Boolean> { isLoading ->
            mBinding.isInventoryLoading = isLoading
        }
        mActivityViewModel.isLoading.observe(this, loadingObserver)
    }

    private fun observeErrorState() {
        val errorObserver = Observer<Boolean> { isError ->
            mBinding.isError = isError
        }
        mActivityViewModel.isError.observe(this, errorObserver)
    }

    private fun initiateSearchListener() {
        mBinding.inventorySearchBar.inventorySearchTextField.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(_p0: CharSequence?, _p1: Int, _p2: Int, _p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, _p1: Int, _p2: Int, _p3: Int) {
                text.let { tempText -> mActivityViewModel.onSearch(tempText.toString()) }
            }

            override fun afterTextChanged(_p0: Editable?) {
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
            return@setOnMenuItemClickListener onMenuSelection(menuItem)
        }
    }

    private fun onMenuSelection(menuItem: MenuItem): Boolean {
        return if (menuItem.order == MenuInventorySearchOptionsOrderInCategory.selectionCategory) {
            menuItem.isChecked = !menuItem.isChecked
            if (menuItem.itemId == R.id.search_include_image_selectable) {
                mActivityViewModel.searchOnlyWithImage = menuItem.isChecked
            }
            false
        } else if (menuItem.order == MenuInventorySearchOptionsOrderInCategory.filterCategory) {
            mActivityViewModel.inventoryFilterSelectedOption.value = menuItem.title.toString()
            true
        } else true
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
