package com.vivek.inventorymanagement.features.inventory.view.viewHolder

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.databinding.GridItemInventoryBinding
import com.vivek.inventorymanagement.features.inventory.model.Item

class ViewHolderGridView(mItemView: View) : RecyclerView.ViewHolder(mItemView), IViewHolder {
    val binding: GridItemInventoryBinding = GridItemInventoryBinding.bind(mItemView)
    override fun bindView(item: Item, isLastItem: Boolean) {
        binding.productItemName.text = item.name
        binding.productItemPrice.text = item.price
        binding.productItemImage.clipToOutline = true
        item.imageUrl?.let {
            binding.productItemImage.load(Uri.parse(item.imageUrl)) {
                placeholder(R.drawable.placeholder_broken_image)
                error(R.drawable.placeholder_broken_image)
            }
            binding.productItemImage.setBackgroundResource(R.drawable.bg_colored_rounded_corner_shape_grid_item)
        }
    }
}