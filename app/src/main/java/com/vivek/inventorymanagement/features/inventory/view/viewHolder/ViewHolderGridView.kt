package com.vivek.inventorymanagement.features.inventory.view.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.databinding.GridItemInventoryBinding

class ViewHolderGridView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: GridItemInventoryBinding = GridItemInventoryBinding.bind(itemView)
}