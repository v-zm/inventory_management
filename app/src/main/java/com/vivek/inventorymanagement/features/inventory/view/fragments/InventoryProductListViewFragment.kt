package com.vivek.inventorymanagement.features.inventory.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.view.adapter.InventoryProductAdapter
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel

class InventoryProductListViewFragment : Fragment(R.layout.fragment_inventory_product_list_view) {
    private lateinit var mAdapter: InventoryProductAdapter
    private val mActivityViewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateRecyclerView(view)
        listenForInventoryData()
    }

    /** [inflateRecyclerView] assigns [InventoryProductAdapter] to recyclerview with id [R.id.product_recycler_view] */
    private fun inflateRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        mAdapter = InventoryProductAdapter(ProductViewTypeEnum.LIST, ArrayList<Item>())
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