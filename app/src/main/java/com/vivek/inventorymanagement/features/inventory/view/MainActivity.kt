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
import kotlinx.coroutines.flow.map

// TODO:: Add page view
// TODO:: Add accessibilty
// TODO:: Add pagination / refresh strategy
// TODO:: Add flow for data from repo
// TODO:: Add java doc
// TODO:: Implement reviews for 1. ViewHolder 2. Dao 3. error in viewmodel
// TODO:: ViewPager for swipe gesture
// TODO:: Address all TODO

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mActivityViewModel: MainActivityViewModel by viewModels()

    /** [onCreate] is used to initiate [MainActivity] and UI setup including Navigation setup */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpNavigation()
    }

    /** [onStart] is used to initiate functions that work with data */
    override fun onStart() {
        super.onStart()
        fetchInventoryProducts()
        observeLoadingState()
        initiateSearchListener()
        inflateFilterMenu()
        observerSelectedFilterMenuOption()
        observeErrorState()
    }

    /** [setUpNavigation] is used to setup Navigation for [MainActivity] using Navigation Graph*/
    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(mBinding.homeBottomNavigationBar, navController = navController)
    }

    /** [fetchInventoryProducts] is used to get inventory item products from [MainActivityViewModel]*/
    private fun fetchInventoryProducts() {
//        mActivityViewModel.getInventoryProducts()
        mActivityViewModel.initViewModel()
    }

    /** [observeLoadingState] is used to start observing @isLoading object from [MainActivityViewModel]*/
    private fun observeLoadingState() {
//        val loadingObserver = Observer<Boolean> { isLoading ->
//            mBinding.isInventoryLoading = isLoading
//        }
//        mActivityViewModel.isLoading.observe(this, loadingObserver)
        mActivityViewModel.loading.map {
            mBinding.isInventoryLoading?.let {
                mBinding.isInventoryLoading = true
            }
        }
    }

    /** [observeErrorState] is used to start observing @isError object from [MainActivityViewModel]*/
    private fun observeErrorState() {
//        val errorObserver = Observer<Unit> { value ->
//            mBinding.setIsError(value)
//        }
//        mActivityViewModel.isError.observe(this, errorObserver)

        mActivityViewModel.error.map {
            mBinding.setIsError(it)
        }
    }

    /** [initiateSearchListener] is used to start observing @inventorySearchTextField for search text from @inventorySearchBar */
    private fun initiateSearchListener() {
        mBinding.inventorySearchBar.inventorySearchTextField.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(_p0: CharSequence?, _p1: Int, _p2: Int, _p3: Int) = Unit

            override fun onTextChanged(text: CharSequence?, _p1: Int, _p2: Int, _p3: Int) {
                text.let { tempText -> mActivityViewModel.onSearch(tempText.toString()) }
            }

            override fun afterTextChanged(_p0: Editable?) = Unit
        })
    }

    /**
     * [inflateFilterMenu] is used to inflate PopupMenu on filterText from @inventorySearchBar
     * Also, it is used to set initial value as first item from [R.menu.inventory_search_options]
     * Also, it is used to observe item click listener on menu widget
     * */
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

    /** [onMenuSelection] is invoked when user selects a menu item from filterText of  @inventorySearchBar */
    private fun onMenuSelection(menuItem: MenuItem): Boolean {
        return when (menuItem.order) {
            MenuInventorySearchOptionsOrderInCategory.selectionCategory -> {
                menuItem.isChecked = !menuItem.isChecked
                if (menuItem.itemId == R.id.search_include_image_selectable) {
                    mActivityViewModel.searchOnlyWithImage = menuItem.isChecked
                }
                false
            }
            MenuInventorySearchOptionsOrderInCategory.filterCategory -> {
                mActivityViewModel.inventoryFilterSelectedOption.value = menuItem.title.toString()
                true
            }
            else -> true
        }
    }

    /**
     * [observerSelectedFilterMenuOption] is used to start observing @inventoryFilterSelectedOption object from [MainActivityViewModel]
     * And bind result with @selectedOption object from [R.layout.inventory_search_bar]
     * */
    private fun observerSelectedFilterMenuOption() {
        val inventorySelectedOptionObserver = Observer<String> { selectedOption ->
            mBinding.inventorySearchBar.selectedOption = selectedOption
        }
        mActivityViewModel.inventoryFilterSelectedOption.observe(
            this, inventorySelectedOptionObserver
        )
    }
}
