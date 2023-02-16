package com.vivek.inventorymanagement.features.inventory.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vivek.inventorymanagement.R
import com.vivek.inventorymanagement.databinding.GridItemInventoryBinding
import com.vivek.inventorymanagement.databinding.ListItemInventoryBinding
import com.vivek.inventorymanagement.features.inventory.enums.ProductViewTypeEnum
import com.vivek.inventorymanagement.features.inventory.model.Item

class InventoryProductAdapter(val viewTypeEnum: ProductViewTypeEnum, var items: List<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderListView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ListItemInventoryBinding = ListItemInventoryBinding.bind(itemView)
//        val productName: AppCompatTextView =
//            itemView.findViewById<AppCompatTextView>(R.id.product_item_name)
//        val productPrice: AppCompatTextView =
//            itemView.findViewById<AppCompatTextView>(R.id.product_item_price)
//
//        val productExtraInfo: AppCompatTextView = itemView.findViewById(R.id.product_extra_info)
//        val divider: MaterialDivider = itemView.findViewById(R.id.item_divider)
//        val imageView:AppCompatImageView =  itemView.findViewById()

    }

    inner class ViewHolderGridView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: GridItemInventoryBinding = GridItemInventoryBinding.bind(itemView)
//        val productName: AppCompatTextView =
//            itemView.findViewById(R.id.product_item_name)
//        val productPrice: AppCompatTextView =
//            itemView.findViewById<AppCompatTextView>(R.id.product_item_price)
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
                val item: Item = items[position]
                viewHolder.binding.productItemName.text = item.name
                viewHolder.binding.productItemPrice.text = item.price
                viewHolder.binding.productExtraInfo.text = item.extra
                viewHolder.binding.productItemImage.load(Uri.parse(item.image)) {
                    placeholder(R.drawable.placeholder_broken_image)
                    error(R.drawable.placeholder_broken_image)
                }
                if (position < items.size - 1) {
                    viewHolder.binding.itemDivider.visibility = View.VISIBLE
                } else {
                    viewHolder.binding.itemDivider.visibility = View.GONE
                }

            }

            ProductViewTypeEnum.GRID -> {
                val viewHolder: ViewHolderGridView = holder as ViewHolderGridView
                val item: Item = items[position]
                viewHolder.binding.productItemName.text = item.name
                viewHolder.binding.productItemPrice.text = item.price
                viewHolder.binding.productItemImage.load(Uri.parse(item.image)) {
                    placeholder(R.drawable.placeholder_broken_image)
                    error(R.drawable.placeholder_broken_image)
                }
            }

        }
    }
}