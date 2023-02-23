package com.vivek.inventorymanagement.data.database.inventory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vivek.inventorymanagement.data.database.inventory.dao.ItemDao
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity


@Database(
    version = 2,
    entities = [ItemEntity::class],
    exportSchema = true,
)
abstract class IInventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}


