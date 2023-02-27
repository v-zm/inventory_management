package com.vivek.inventorymanagement.features.inventory.view.viewHolder

import com.vivek.inventorymanagement.features.inventory.model.Item

interface IViewHolder {
    fun bindView(item: Item, isLastItem: Boolean)
}