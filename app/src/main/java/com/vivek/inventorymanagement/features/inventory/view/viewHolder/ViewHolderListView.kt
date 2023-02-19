package com.vivek.inventorymanagement.features.inventory.view.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.databinding.ListItemInventoryBinding

class ViewHolderListView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ListItemInventoryBinding = ListItemInventoryBinding.bind(itemView)
}