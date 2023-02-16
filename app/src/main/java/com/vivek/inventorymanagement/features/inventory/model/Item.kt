package com.vivek.inventorymanagement.features.inventory.model

import com.vivek.inventorymanagement.dtos.InventoryItemDto

class Item(
    val name: String,
    val price: String,
    val extra: String?,
    val image: String
) {
    companion object {
        fun getItemFromItemsDto(item: InventoryItemDto): Item {
            return Item(
                name = item.name,
                price = item.price,
                extra = item.extra,
                image = item.image
            )
        }
    }
}