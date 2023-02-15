package com.vivek.inventorymanagement.features.inventory.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDivider
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.model.Item

class InventoryProductAdapter(val viewTypeEnum: ProductViewTypeEnum, var items: List<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderListView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productName: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.product_item_name)
        val productPrice: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.product_item_price)

        val productExtraInfo: AppCompatTextView = itemView.findViewById(R.id.product_extra_info)
        val divider: MaterialDivider = itemView.findViewById(R.id.item_divider)

    }

    inner class ViewHolderGridView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: AppCompatTextView =
            itemView.findViewById(R.id.product_item_name)
        val productPrice: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.product_item_price)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return when (viewTypeEnum) {
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
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeEnum.type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (viewTypeEnum) {
            ProductViewTypeEnum.LIST -> {
                val viewHolder: ViewHolderListView = holder as ViewHolderListView
                viewHolder.productName.text = items[position].name
                viewHolder.productPrice.text = items[position].price
                viewHolder.productExtraInfo.text = items[position].extra
                if (position < items.size-1) {
                    viewHolder.divider.visibility = View.VISIBLE
                }else{
                    viewHolder.divider.visibility = View.GONE
                }
            }

            ProductViewTypeEnum.GRID -> {
                val viewHolder: ViewHolderGridView = holder as ViewHolderGridView
                viewHolder.productName.text = items[position].name
                viewHolder.productPrice.text = items[position].price
            }

        }
    }
}