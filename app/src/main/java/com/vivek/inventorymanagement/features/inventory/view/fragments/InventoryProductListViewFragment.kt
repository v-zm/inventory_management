package com.vivek.inventorymanagement.features.inventory.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.view.adapter.InventoryProductAdapter
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel

class InventoryProductListViewFragment() : Fragment(R.layout.fragment_inventory_product_list_view) {
    private lateinit var mAdapter: InventoryProductAdapter
    private val mActivityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_product_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        mAdapter = InventoryProductAdapter(ProductViewTypeEnum.LIST, ArrayList<Item>())
        recyclerView.adapter = mAdapter
        listenForInventoryData()
    }

    private fun listenForInventoryData() {
        val inventoryListObserver = Observer<List<Item>> { newItemList ->
            mAdapter.items = newItemList
            mAdapter.notifyDataSetChanged()
        }
        mActivityViewModel.inventoryItemList.observe(viewLifecycleOwner, inventoryListObserver)
    }

}