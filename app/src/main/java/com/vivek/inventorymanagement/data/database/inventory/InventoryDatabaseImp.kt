package com.vivek.inventorymanagement.data.database.inventory

import android.content.Context
import androidx.room.Room
import com.vivek.inventorymanagement.data.database.inventory.migrations.InventoryDatabaseMigration
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class InventoryDatabaseImp @Inject constructor(@ApplicationContext applicationContext: Context) {
    val _inventoryDB: InventoryDatabase =
        Room.databaseBuilder(applicationContext, InventoryDatabase::class.java, "inventory-db")
//            .addMigrations(InventoryDatabaseMigration.MIGRATION_1_2)
            .build()

    fun getInventoryDatabase(): InventoryDatabase {
        return _inventoryDB
    }
}