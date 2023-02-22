package com.vivek.inventorymanagement.data.database.inventory

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.vivek.inventorymanagement.data.database.inventory.migrations.ItemDaoMigration
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val TEST_DB = "migration_Test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        InventoryDatabaseImp::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
       var db= helper.createDatabase(TEST_DB, 1).apply { close() }

      val inventoryDb:IInventoryDatabase=  Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            IInventoryDatabase::class.java,
            TEST_DB
        )
            .addMigrations(ItemDaoMigration.MIGRATION_1_2).build().apply {
                openHelper.writableDatabase.close()
            }
     helper.runMigrationsAndValidate(TEST_DB,2,true, ItemDaoMigration.MIGRATION_1_2)
    }
}