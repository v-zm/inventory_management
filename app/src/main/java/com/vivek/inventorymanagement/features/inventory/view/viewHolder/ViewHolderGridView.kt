package com.vivek.inventorymanagement.features.inventory.view.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.databinding.GridItemInventoryBinding

class ViewHolderGridView(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
    val binding: GridItemInventoryBinding = GridItemInventoryBinding.bind(mItemView)
}