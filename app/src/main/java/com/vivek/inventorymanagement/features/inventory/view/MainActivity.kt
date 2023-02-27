package com.vivek.inventorymanagement.features.inventory.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.databinding.ActivityMainBinding
import com.vivek.inventorymanagement.features.inventory.model.MenuInventorySearchOptionsOrderInCategory
import com.vivek.inventorymanagement.features.inventory.view.adapter.ViewPagerStateAdapter
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO:: Add java doc
// TODO:: Address all TODO

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var viewPagerAdapter: ViewPagerStateAdapter

    /** [onCreate] is used to initiate [MainActivity] and UI setup including Navigation setup */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpPagerNavigation()

    }

    /** [onResume] is used to initiate functions that work with data */

    override fun onResume() {
        super.onResume()
        observeLoadingState()
        initiateSearchListener()
        fetchInventoryProducts()
        inflateFilterMenu()
        observerSelectedFilterMenuOption()
        observeErrorState()
    }

    /** [setUpNavigation] is used to setup Navigation listeners for [MainActivity] using ViewPager and Bottom Nav */
    private fun setUpPagerNavigation() {
        viewPagerAdapter = ViewPagerStateAdapter(this@MainActivity)
        mBinding.viewPager.adapter = viewPagerAdapter

        mBinding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mBinding.homeBottomNavigationBar.selectedItemId =
                    mBinding.homeBottomNavigationBar.menu.getItem(position).itemId
            }
        })
        mBinding.homeBottomNavigationBar.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.menu_item_product_list -> {
                    mBinding.viewPager.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.menu_item_product_grid -> {
                    mBinding.viewPager.currentItem = 1
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }


    /** [fetchInventoryProducts] is used to get inventory item products from [MainActivityViewModel]*/
    private fun fetchInventoryProducts() {
        mActivityViewModel.initViewModel()
    }

    /** [observeLoadingState] is used to start observing @isLoading object from [MainActivityViewModel]*/
    private fun observeLoadingState() {
        val loadingObserver = Observer<Boolean> { isLoading ->
            mBinding.isInventoryLoading = isLoading
        }
        mActivityViewModel.isLoading.observe(this, loadingObserver)
    }

    /** [observeErrorState] is used to start observing @isError object from [MainActivityViewModel]*/
    private fun observeErrorState() {
        val errorObserver = Observer<Unit> { value ->
            mBinding.setIsError(value)
        }
        mActivityViewModel.isError.observe(this, errorObserver)

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
