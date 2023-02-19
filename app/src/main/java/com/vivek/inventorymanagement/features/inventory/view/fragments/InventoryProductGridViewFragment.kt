package com.vivek.inventorymanagement.features.inventory.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.view.adapter.InventoryProductAdapter
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel

class InventoryProductGridViewFragment : Fragment(R.layout.fragment_inventory_product_grid_view) {
    private val mActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var mAdapter: InventoryProductAdapter

    companion object {
        private const val GRID_ITEM_COUNT: Int = 3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateRecyclerView(view)
        listenForInventoryData()
    }

    /** [inflateRecyclerView] assigns [InventoryProductAdapter] to recyclerview with id [R.id.product_recycler_view] */
    private fun inflateRecyclerView(view: View) {
        val recyclerView: RecyclerView =
            view.findViewById<RecyclerView>(R.id.product_recycler_view)
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

}
