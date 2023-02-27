package com.vivek.inventorymanagement.features.inventory.view.viewHolder

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.databinding.ListItemInventoryBinding
import com.vivek.inventorymanagement.features.inventory.model.Item

class ViewHolderListView(mItemView: View) : RecyclerView.ViewHolder(mItemView), IViewHolder {
    val binding: ListItemInventoryBinding = ListItemInventoryBinding.bind(mItemView)
    override fun bindView(item:Item,isLastItem: Boolean) {
        binding.productItemName.text = item.name
        binding.productItemPrice.text = item.price
        binding.productExtraInfo.text = item.extra

        item.imageUrl?.let {
            binding.productItemImage.load(Uri.parse(item.imageUrl)) {
                placeholder(R.drawable.placeholder_broken_image)
                error(R.drawable.placeholder_broken_image)
            }
            binding.productItemImage.setBackgroundResource(R.drawable.bg_colored_rounded_corner_shape_grid_item)
        }

        if (!isLastItem) {
            binding.itemDivider.visibility = View.VISIBLE
        } else {
            binding.itemDivider.visibility = View.GONE
        }
    }


}