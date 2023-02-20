package com.vivek.inventorymanagement.features.inventory.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.view.viewHolder.ViewHolderGridView
import com.vivek.inventorymanagement.features.inventory.view.viewHolder.ViewHolderListView

class InventoryProductAdapter(
    private val mViewTypeEnum: ProductViewTypeEnum,
    private var mItems: List<Item>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /** [updateInventoryItems] is used to update [mItems] for adapter*/
    fun updateInventoryItems(newItems: List<Item>) {
        mItems = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return when (mViewTypeEnum) {
            ProductViewTypeEnum.LIST -> ViewHolderListView(
                inflater.inflate(
                    R.layout.list_item_inventory, parent, false
                )
            )
            ProductViewTypeEnum.GRID -> ViewHolderGridView(
                inflater.inflate(
                    R.layout.grid_item_inventory, parent, false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return mViewTypeEnum.type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (mViewTypeEnum) {
            ProductViewTypeEnum.LIST -> {
                bindViewForViewHolderListView(
                    holder,
                    item = mItems[position],
                    isLastItem = position == mItems.size - 1
                )
            }

            ProductViewTypeEnum.GRID -> {
                bindViewForViewHolderGridView(holder, item = mItems[position])
            }

        }
    }

    private fun bindViewForViewHolderGridView(holder: RecyclerView.ViewHolder, item: Item) {
        val viewHolder: ViewHolderGridView = holder as ViewHolderGridView
        viewHolder.binding.productItemName.text = item.name
        viewHolder.binding.productItemPrice.text = item.price
        viewHolder.binding.productItemImage.clipToOutline = true
        item.imageUrl?.let {
            viewHolder.binding.productItemImage.load(Uri.parse(item.imageUrl)) {
                placeholder(R.drawable.placeholder_broken_image)
                error(R.drawable.placeholder_broken_image)
            }
            viewHolder.binding.productItemImage.setBackgroundResource(R.drawable.bg_colored_rounded_corner_shape_grid_item)
        }
    }

    private fun bindViewForViewHolderListView(
        holder: RecyclerView.ViewHolder,
        item: Item,
        isLastItem: Boolean
    ) {
        val viewHolder: ViewHolderListView = holder as ViewHolderListView

        viewHolder.binding.productItemName.text = item.name
        viewHolder.binding.productItemPrice.text = item.price
        viewHolder.binding.productExtraInfo.text = item.extra

        item.imageUrl?.let {
            viewHolder.binding.productItemImage.load(Uri.parse(item.imageUrl)) {
                placeholder(R.drawable.placeholder_broken_image)
                error(R.drawable.placeholder_broken_image)
            }
            viewHolder.binding.productItemImage.setBackgroundResource(R.drawable.bg_colored_rounded_corner_shape_grid_item)
        }

        if (!isLastItem) {
            viewHolder.binding.itemDivider.visibility = View.VISIBLE
        } else {
            viewHolder.binding.itemDivider.visibility = View.GONE
        }
    }
}