package com.vivek.inventorymanagement.features.inventory.view.helper

import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.setup.repository.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ItemSearchHelperTest {
    lateinit var itemSearchHelper: ItemSearchHelper
    private val testRepository: FakeRepository = FakeRepository()
    private var searchedItems: List<Item> = listOf()

    @Before
    fun setup() {
        itemSearchHelper = ItemSearchHelper(testRepository)
        searchedItems= listOf()
    }

    /**
     * Test with default values
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun search_PassDefaultValuesWithVariation_ReturnEveryItem() = runTest {


        /**
         *  Pass searchText ="", selectedFilter=null, searchOnlyWithImage=false
         *  Comes with whole list
         * */

        searchedItems = itemSearchHelper.search("", null, false)
        assert(searchedItems.size == testRepository.items.size)

        /**
         *  Pass searchText ="", selectedFilter=null, searchOnlyWithImage=true
         *  Comes with every item with image
         * */

        searchedItems = itemSearchHelper.search("", null, true)
        searchedItems.forEach { each ->
            assert(each.imageUrl != null)
            each.imageUrl?.let { assert(it.isNotEmpty()) }
        }

        /**
         *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchOnlyWithImage=true
         *  Comes with every item with image
         * */

        searchedItems =
            itemSearchHelper.search("", InventoryFilterOptionEnum.FILTER_BY_NAME.option, true)
        searchedItems.forEach { each ->
            assert(each.imageUrl != null)
            each.imageUrl?.let { assert(it.isNotEmpty()) }
        }

        /**
         *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchOnlyWithImage=false
         *  Comes with every item
         * */

        searchedItems =
            itemSearchHelper.search("", InventoryFilterOptionEnum.FILTER_BY_NAME.option, false)
        assert(searchedItems.size == testRepository.items.size)


        /**
         *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchOnlyWithImage=true
         *  Comes with every item with image
         * */

        searchedItems =
            itemSearchHelper.search("", InventoryFilterOptionEnum.FILTER_BY_PRICE.option, true)
        searchedItems.forEach { each ->
            assert(each.imageUrl != null)
            each.imageUrl?.let { assert(it.isNotEmpty()) }
        }

        /**
         *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchOnlyWithImage=false
         *  Comes with every item with image
         * */

        searchedItems =
            itemSearchHelper.search("", InventoryFilterOptionEnum.FILTER_BY_PRICE.option, false)
        assert(searchedItems.size == testRepository.items.size)


        /**
         *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchOnlyWithImage=true
         *  Comes with every item with image
         * */

        searchedItems =
            itemSearchHelper.search("", InventoryFilterOptionEnum.NO_FILTER.option, true)
        searchedItems.forEach { each ->
            assert(each.imageUrl != null)
            each.imageUrl?.let { assert(it.isNotEmpty()) }
        }

        /**
         *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchOnlyWithImage=false
         *  Comes with every item with image
         * */

        searchedItems =
            itemSearchHelper.search("", InventoryFilterOptionEnum.NO_FILTER.option, false)
        assert(searchedItems.size == testRepository.items.size)
    }


    /**
     * Test by passing searchText = "Item" and variations in selectedFilter, searchWithONlyImage
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun search_PassSearchTextWithItemNames_ReturnEveryItemThatMatchesSearchText() = runTest {
        val searchText: String = "Item"
        /**
         * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=false
         * return all items with name or price column matches "Item"
         * */

        searchedItems = itemSearchHelper.search(searchText, null, false)
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=false
         * return all items with name column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_NAME.option,
                false
            )
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=false
         * return all items with price column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_PRICE.option,
                false
            )
        searchedItems.forEach { each ->
            assert(each.price.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=false
         * return all items with name or price column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(searchText, InventoryFilterOptionEnum.NO_FILTER.option, false)
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
        }


        /**
         * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=true
         * return all items with name or price column matches "Item". Also, each should have image
         * */
        searchedItems = itemSearchHelper.search(searchText, null, true)

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=true
         * return all items with name column matches "Item". Also, each should have image
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_NAME.option,
                true
            )

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=true
         * return all items with price column matches "Item". Also, each should have image
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_PRICE.option,
                true
            )
        searchedItems.forEach { each ->
            assert(each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=true
         * return all items with name or price column matches "Item". Also, each Item should have image
         * */
        searchedItems =
            itemSearchHelper.search(searchText, InventoryFilterOptionEnum.NO_FILTER.option, true)

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }

    }


    /**
     * Test by passing searchText= "1000" in items and variations in selectedFilter, searchWithONlyImage
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun search_PassSearchTextWithItemPrice1000_ReturnEveryItemThatMatchesSearchText() = runTest {
        val searchText: String = "1000"
        /**
         * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=false
         * return all items with name or price column matches "Item"
         * */

        searchedItems = itemSearchHelper.search(searchText, null, false)
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=false
         * return all items with name column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_NAME.option,
                false
            )
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=false
         * return all items with price column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_PRICE.option,
                false
            )
        searchedItems.forEach { each ->
            assert(each.price.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=false
         * return all items with name or price column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(searchText, InventoryFilterOptionEnum.NO_FILTER.option, false)
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
        }


        /**
         * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=true
         * return all items with name or price column matches "Item". Also, each should have image
         * */
        searchedItems = itemSearchHelper.search(searchText, null, true)

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=true
         * return all items with name column matches "Item". Also, each should have image
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_NAME.option,
                true
            )

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=true
         * return all items with price column matches "Item". Also, each should have image
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_PRICE.option,
                true
            )
        searchedItems.forEach { each ->
            assert(each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=true
         * return all items with name or price column matches "Item". Also, each Item should have image
         * */
        searchedItems =
            itemSearchHelper.search(searchText, InventoryFilterOptionEnum.NO_FILTER.option, true)

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
    }


    /**
     * Test by passing searchText= "5000" in items and variations in selectedFilter, searchWithONlyImage
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun search_PassSearchTextWithItemPrice5000_ReturnEveryItemThatMatchesSearchText() = runTest {
        val searchText: String = "5000"
        /**
         * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=false
         * return all items with name or price column matches "Item"
         * */

        searchedItems = itemSearchHelper.search(searchText, null, false)
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=false
         * return all items with name column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_NAME.option,
                false
            )
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=false
         * return all items with price column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_PRICE.option,
                false
            )
        searchedItems.forEach { each ->
            assert(each.price.contains(searchText))
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=false
         * return all items with name or price column matches "Item"
         * */
        searchedItems =
            itemSearchHelper.search(searchText, InventoryFilterOptionEnum.NO_FILTER.option, false)
        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
        }


        /**
         * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=true
         * return all items with name or price column matches "Item". Also, each should have image
         * */
        searchedItems = itemSearchHelper.search(searchText, null, true)

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }

        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=true
         * return all items with name column matches "Item". Also, each should have image
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_NAME.option,
                true
            )

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=true
         * return all items with price column matches "Item". Also, each should have image
         * */
        searchedItems =
            itemSearchHelper.search(
                searchText,
                InventoryFilterOptionEnum.FILTER_BY_PRICE.option,
                true
            )
        searchedItems.forEach { each ->
            assert(each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }
        /**
         * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=true
         * return all items with name or price column matches "Item". Also, each Item should have image
         * */
        searchedItems =
            itemSearchHelper.search(searchText, InventoryFilterOptionEnum.NO_FILTER.option, true)

        searchedItems.forEach { each ->
            assert(each.name.contains(searchText) || each.price.contains(searchText))
            assert(each.imageUrl != null)
            each.imageUrl?.let { image ->
                assert(image.isNotEmpty())
            }
        }

    }
}