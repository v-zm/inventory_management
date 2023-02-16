package com.vivek.inventorymanagement.features.inventory.model

import com.vivek.inventorymanagement.data.api.dtos.InventoryItemDto
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity

class Item(
    val name: String,
    val price: String,
    val extra: String?,
    val imageUrl: String
) {
    companion object {
        fun getItemFromItemDto(item: InventoryItemDto): Item {
            return Item(
                name = item.name,
                price = item.price,
                extra = item.extra,
                imageUrl = item.imageUrl
            )
        }

        fun getItemFromItemEntity(item: ItemEntity): Item {
            return Item(
                name = item.name,
                price = item.price,
                extra = item.extra,
                imageUrl = item.imageUrl
            )
        }
    }
}