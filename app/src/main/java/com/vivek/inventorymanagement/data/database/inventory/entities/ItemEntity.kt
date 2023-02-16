package com.vivek.inventorymanagement.data.database.inventory.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vivek.inventorymanagement.features.inventory.model.Item

@Entity(tableName = "item")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "extra") val extra: String?,
    @ColumnInfo(name = "image") val imageUrl: String
) {
    companion object {
        fun getItemEntity(item: Item): ItemEntity {
            return ItemEntity(
                name = item.name,
                price = item.price,
                extra = item.extra,
                imageUrl = item.imageUrl
            )
        }
    }
}