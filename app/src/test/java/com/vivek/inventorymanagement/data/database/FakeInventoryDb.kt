package com.vivek.inventorymanagement.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.vivek.inventorymanagement.data.database.inventory.IInventoryDatabase
import com.vivek.inventorymanagement.data.database.inventory.InventoryDatabaseImp
import com.vivek.inventorymanagement.data.database.inventory.migrations.ItemDaoMigration

class FakeInventoryDb(val context: Context = ApplicationProvider.getApplicationContext()) :
    InventoryDatabaseImp(context) {
    override fun getInventoryDatabase(): IInventoryDatabase {
        return Room.databaseBuilder(context, IInventoryDatabase::class.java, "inventory-db")
            .allowMainThreadQueries()
            .addMigrations(ItemDaoMigration.MIGRATION_1_2)
            .build()
    }
}