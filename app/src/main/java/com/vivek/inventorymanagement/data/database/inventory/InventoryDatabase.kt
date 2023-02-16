package com.vivek.inventorymanagement.data.database.inventory

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.vivek.inventorymanagement.data.database.inventory.dao.ItemDao
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity


@Database(
    version = 1,
    entities = [ItemEntity::class],
    exportSchema = false,

)
abstract class InventoryDatabase : RoomDatabase() {
    class MyAutoMigration : AutoMigrationSpec

    abstract fun itemDao(): ItemDao
}


