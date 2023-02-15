package com.vivek.inventorymanagement.features.inventory.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.view.adapter.InventoryProductAdapter

class InventoryProductGridViewFragment() : Fragment(R.layout.fragment_inventory_product_grid_view) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_product_grid_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView =
            view.findViewById<RecyclerView>(R.id.product_recycler_view)
        recyclerView.adapter = InventoryProductAdapter(ProductViewTypeEnum.GRID)
    }




}