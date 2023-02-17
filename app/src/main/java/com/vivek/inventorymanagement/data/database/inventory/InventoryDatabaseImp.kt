package com.vivek.inventorymanagement.data.database.inventory

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class InventoryDatabaseImp @Inject constructor(@ApplicationContext applicationContext: Context) {
    val _inventoryDB: IInventoryDatabase =
        Room.databaseBuilder(applicationContext, IInventoryDatabase::class.java, "inventory-db")
            .build()

    fun getInventoryDatabase(): IInventoryDatabase {
        return _inventoryDB
    }

}