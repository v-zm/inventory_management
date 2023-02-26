package com.vivek.inventorymanagement.features.inventory.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.databinding.FragmentInventoryProductGridViewBinding
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.view.adapter.InventoryProductAdapter
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryViewState

class InventoryProductGridViewFragment : Fragment(R.layout.fragment_inventory_product_grid_view) {
    private val mActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var mAdapter: InventoryProductAdapter

    companion object {
        private const val GRID_ITEM_COUNT: Int = 3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateRecyclerView(view)
//        listenForInventoryData()
        listener()
    }

    /** [inflateRecyclerView] assigns [InventoryProductAdapter] to recyclerview with id [R.id.product_recycler_view] */
    private fun inflateRecyclerView(view: View) {
        val binding: FragmentInventoryProductGridViewBinding =
            FragmentInventoryProductGridViewBinding.bind(view)
        val recyclerView: RecyclerView =
            binding.productRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, GRID_ITEM_COUNT)
        mAdapter = InventoryProductAdapter(ProductViewTypeEnum.GRID, ArrayList<Item>())
        recyclerView.adapter = mAdapter
    }

    /**
     * [listenForInventoryData] observes on @inventoryItemList liveData from [MainActivityViewModel]
     * And, calls @updateInventoryItems function in [InventoryProductAdapter] to update items
     * */
    private fun listenForInventoryData() {
        val inventoryListObserver = Observer<List<Item>> { newItemList ->
            mAdapter.updateInventoryItems(newItemList)
        }
        mActivityViewModel.inventoryItemList.observe(viewLifecycleOwner, inventoryListObserver)
    }

    private fun listener() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mActivityViewModel.getFlow().collect { newItemList ->
                when (newItemList) {
                    is InventoryViewState.Error -> print("Error")
                    is InventoryViewState.Success -> mAdapter.updateInventoryItems(newItemList.items)
                    is InventoryViewState.Loading -> print("Loading")
                }
            }
        }
    }

}
