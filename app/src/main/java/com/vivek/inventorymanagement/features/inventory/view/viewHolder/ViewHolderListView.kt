package com.vivek.inventorymanagement.features.inventory.view.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.databinding.ListItemInventoryBinding

class ViewHolderListView(mItemView: View) : RecyclerView.ViewHolder(mItemView) {
    val binding: ListItemInventoryBinding = ListItemInventoryBinding.bind(mItemView)
}