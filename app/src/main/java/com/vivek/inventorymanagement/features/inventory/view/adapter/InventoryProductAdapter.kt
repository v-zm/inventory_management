package com.vivek.inventorymanagement.features.inventory.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.view.viewHolder.IViewHolder
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
        (holder as IViewHolder).bindView(
            item = mItems[position],
            isLastItem = position == mItems.size - 1
        )
    }

}