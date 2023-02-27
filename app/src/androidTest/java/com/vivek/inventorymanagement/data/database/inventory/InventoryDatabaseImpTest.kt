package com.vivek.inventorymanagement.data.database.inventory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vivek.inventorymanagement.data.database.inventory.dao.ItemDao
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity
import com.vivek.inventorymanagement.features.inventory.model.Item
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class InventoryDatabaseImpTest {
    private lateinit var db: IInventoryDatabase
    private lateinit var itemDao: ItemDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, IInventoryDatabase::class.java).build()
        itemDao = db.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * Item(Item0,1000,https://hamcrest.org/images/logo.jpg,Extra 0)
     * Item(Item1,2000,null,Extra 1)
     * Item(Item2,3000,null,Extra 2)
     * Item(Item3,4000,https://hamcrest.org/images/logo.jpg,Extra 3)
     * Item(Item4,5000,null,Extra 4)
     * Item(Item5,6000,null,Extra 5)
     * Item(Item6,7000,https://hamcrest.org/images/logo.jpg,Extra 6)
     * Item(Item7,8000,null,Extra 7)
     * Item(Item8,9000,null,Extra 8)
     * Item(Item9,10000,https://hamcrest.org/images/logo.jpg,Extra 9)
     * */
    @Test
    fun emptyDb_insert10Items_Pass() = runBlocking {
        val items: List<Item> = List(10) { index ->
            Item(
                extra = "Extra $index",
                imageUrl = if (index % 3 == 0) "https://hamcrest.org/images/logo.jpg" else null,
                price = "${(index % 10) * 1000 + 1000}",
                name = "Item$index"
            )
        }
        val itemEntities = items.map { each ->
            ItemEntity.getItemEntity(each)
        }
        itemDao.insertAll(itemEntities)
        assertEquals(10, itemDao.getAll().size)
    }

    /**
     * search by name = Item 1
     * and get items by name
     * Result = 1 Item
     * */
    @Test
    fun getItemsByName_Pass_Item1_as_searchText_and_get_items_with_name_containing_values() =
        runBlocking {
            emptyDb_insert10Items_Pass()

            val searchText: String = "Item1"
            val itemEntities: List<ItemEntity> = itemDao.getItemsByName(searchText)
            val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
            assertEquals(1, items.size)
            items.forEach { each ->
                assert(each.name.contains(searchText))
            }
        }

    /**
     * search by name = Book
     * and get items by name
     * Result = 0 items
     * */
    @Test
    fun getItemsByName_Pass_Book_as_searchText_and_get_zero_result() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "Book"
        val itemEntities = itemDao.getItemsByName(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }

    /**
     * search by price = 100
     * get 2 items
     * all items have price 100
     * Result=2 items
     * */
    @Test
    fun getItemsByPrice_Pass_100_as_searchText_and_get_3_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "100"
        val itemEntities = itemDao.getItemsByPrice(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(2, items.size)
        items.forEach { each ->
            assert(each.price.contains(searchText))
        }
    }


    /**
     * search by price = 440
     * Result= 0 items
     * */

    @Test
    fun getItemByPrice_Pass_440_as_searchText_and_get_zero_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "440"
        val itemEntities = itemDao.getItemsByPrice(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }

    /**
     * search by nameAndImage = Item,
     * Result= 4 items
     * all items have name Item in them and it should have image
     * */
    @Test
    fun getItemByNameAndImage_Pass_Item_as_searchText_and_get_4_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "Item"
        val itemEntities = itemDao.getItemsByNameAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(4, items.size)
        items.forEach { each ->
            assert(each.name.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
    }

    /**
     * search by name = Item1, it should have image
     * Result= 0 items
     * */
    @Test
    fun getItemByNameAndImage_Pass_Item1_as_searchText_and_get_zero_result() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "Item1"
        val itemEntities = itemDao.getItemsByNameAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }

    /**
     * search by name = Item, it should have image
     * Result= 0 items
     * */
    @Test
    fun getItemByNameAndImage_Pass_Book_as_searchText_and_get_zero_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "Book"
        val itemEntities = itemDao.getItemsByNameAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }


    /**
     * search by price = 0, it should have image
     * Result= 3 items
     *
     * */
    @Test
    fun getItemByPriceAndImage_Pass_0_as_searchText_And_get_result() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "0"
        val itemEntities = itemDao.getItemsByPriceAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(4, items.size)
    }

    /**
     * search by price = 100, it should have image
     * Result= 1 items
     * */
    @Test
    fun getItemByPriceAndImage_Pass_100_as_searchText_And_get_zero_result() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "100"
        val itemEntities = itemDao.getItemsByPriceAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(2, items.size)
    }

    /**
     * search by price = 300, it should have image
     * Result= 0 items
     * all items have name Item in them
     * */
    @Test
    fun getItemByPriceAndImage_Pass_300_as_searchText_And_4_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "300"
        val itemEntities = itemDao.getItemsByPriceAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }

    /**
     * search by price = 250, it should have image
     * Result = 0
     * */
    @Test
    fun getItemsByNameAndImage_Pass_250_as_searchText_And_0_result() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "250"
        val itemEntities = itemDao.getItemsByNameAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }

    /**
     * search by name or price = Item
     * Result= 10 items
     * all items have name Item in them
     * */
    fun getItemsByNameOrPrice_Pass_Item_as_searchText_and_get_10_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "Item"
        val itemEntities = itemDao.getItemsByNameOrPrice(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(10, items.size)
        items.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
        }
    }

    /**
     * search by name or price = 300
     * Result= 1 items
     * all items have name Item in them
     * */
    fun getItemsByNameOrPrice_Pass_300_as_searchText_And_get_3_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "300"
        val itemEntities = itemDao.getItemsByNameOrPrice(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(1, items.size)
        items.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
        }
    }

    /**
     * search by price = Book
     * Result= 0
     * */
    @Test
    fun getItemByNameOrPrice_Pass_Book_as_searchText_And_get_zero_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "Book"
        val itemEntities = itemDao.getItemsByNameOrPrice(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }

    /**
     * search by price = 250
     * Result = 0
     * */
    @Test
    fun getItemsByNameOrPrice_Pass_250_as_searchText_And_get_zero_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "250"
        val itemEntities = itemDao.getItemsByNameOrPrice(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }

    /**
     * search by name or price = Item, it should have image
     * Result= 4 items
     * all items have name Item in them
     * */
    @Test
    fun searchByNameOrPriceAndImage_Pass_Item_as_searchText_and_get_3_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "Item"
        val itemEntities = itemDao.getItemsByNameOrPriceAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(4, items.size)
        items.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
    }

    /**
     * search by name or price = 300, it should have image
     * Result=0 items
     * all items have name Item in them
     * */
    @Test
    fun searchByNameOrPriceAndImage_Pass_300_as_searchText_and_get_3_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "300"
        val itemEntities = itemDao.getItemsByNameOrPriceAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(0, items.size)
    }

    /**
     * search by name or price = 4000, it should have image
     * Result= 1 items
     * all items have name Item in them
     * */
    @Test
    fun searchByNameOrPriceAndImage_Pass_4000_as_searchText_and_get_1_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "4000"
        val itemEntities = itemDao.getItemsByNameOrPriceAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(1, items.size)
        items.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
    }

    /**
     * search by name or price = Item6, it should have image
     * get 1 items
     * all items have name Item in them
     * */
    @Test
    fun searchByNameOrPriceAndImage_Pass_Item6_as_searchText_and_get_1_results() = runBlocking {
        emptyDb_insert10Items_Pass()
        val searchText = "Item6"
        val itemEntities = itemDao.getItemsByNameOrPriceAndImage(searchText)
        val items = itemEntities.map { each -> Item.getItemFromItemEntity(each) }
        assertEquals(1, items.size)
        items.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
    }

    @Test
    fun delete_Pass_one_Item_And_it_should_be_deleted() = runBlocking {
        emptyDb_insert10Items_Pass()
        var items: List<ItemEntity> = itemDao.getAll()
        val itemToDelete: ItemEntity = items.first()
        itemDao.delete(itemToDelete)
        items = itemDao.getAll()

        items.forEach { each ->
            assertNotEquals(each.uid, itemToDelete.uid)
        }
    }

    @Test
    fun deleteAll_Pass_every_Item_And_DB_should_be_empty() = runBlocking {
        emptyDb_insert10Items_Pass()
        var items: List<ItemEntity> = itemDao.getAll()
        itemDao.deleteAll()
        items = itemDao.getAll()
        assertEquals(0, items.size)
    }


}