package com.vivek.inventorymanagement.data.database.inventory

import android.content.Context
import androidx.room.Room
import com.vivek.inventorymanagement.data.database.inventory.migrations.ItemDaoMigration
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class InventoryDatabaseImp @Inject constructor(@ApplicationContext mApplicationContext: Context) {
    private val mInventoryDB: IInventoryDatabase =
        Room.databaseBuilder(mApplicationContext, IInventoryDatabase::class.java, "inventory-db")
            .addMigrations(ItemDaoMigration.MIGRATION_1_2)
            .build()

    fun getInventoryDatabase(): IInventoryDatabase {
        return mInventoryDB
    }

}