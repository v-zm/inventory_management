package com.vivek.inventorymanagement.data.database.inventory.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object ItemDaoMigration {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE item ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0 ")
        }
    }
}