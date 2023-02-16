package com.vivek.inventorymanagement.data.database.inventory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vivek.inventorymanagement.data.database.inventory.dao.ItemDao
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity


@Database(entities = [ItemEntity::class], version = 2)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}


