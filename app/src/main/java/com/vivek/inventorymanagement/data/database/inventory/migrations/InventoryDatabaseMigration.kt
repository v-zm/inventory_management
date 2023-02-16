package com.vivek.inventorymanagement.data.database.inventory.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class InventoryDatabaseMigration {
    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE item Add default 0 for id")
            }
        }
    }


}