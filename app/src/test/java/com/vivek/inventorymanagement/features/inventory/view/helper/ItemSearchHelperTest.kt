package com.vivek.inventorymanagement.features.inventory.view.helper

import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryItemFetchState
import com.vivek.inventorymanagement.setup.repository.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ItemSearchHelperTest {
    private val itemCount: Int = 15
    private val testRepository: FakeRepository = FakeRepository(itemCount)
    private var searchedItems: List<Item> = listOf()

    @Before
    fun setup() {
        searchedItems = listOf()
    }

    /**
     * Test with default values
     *
     * Pass searchText ="", selectedFilter=null, searchOnlyWithImage=false
     * Comes with whole list
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `search() pass searchText='' selectedFilter=null, searchOnlyWithImage=false and returns every item`() =
        runTest {
            val flow: Flow<InventoryItemFetchState> = testRepository.getInventoryItems(
                "",
                InventoryFilterOptionEnum.FILTER_BY_NAME,
                false
            )

            flow.collectIndexed { index, value ->
                when(index){
                    0 -> {
                        assert(value == InventoryItemFetchState.Loading(true))
                    }
                    1->{
                        assert(value==InventoryItemFetchState.Success(testRepository.getInventoryItems()))
                    }
                    2 -> {
                        assert(value == InventoryItemFetchState.Loading(false))
                    }
                }
            }

        }
//
//    /**
//     *  Pass searchText ="", selectedFilter=null, searchOnlyWithImage=true
//     *  Comes with every item with image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText = '' , selectedFilter=null, searchOnlyWithImage=true and returns every items with image`() =
//        runTest {
//            searchedItems = itemSearchHelper.search("", null, true)
//            searchedItems.forEach { each ->
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { assert(it.isNotEmpty()) }
//            }
//
//        }
//
//
//    /**
//     *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchOnlyWithImage=true
//     *  Comes with every item with image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText='',selectedFilter=FILTER_BY_NAME searchOnlyWithImage=true and returns every item with image`() =
//        runTest {
//            searchedItems =
//                itemSearchHelper.search("", InventoryFilterOptionEnum.FILTER_BY_NAME.option, true)
//            searchedItems.forEach { each ->
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { assert(it.isNotEmpty()) }
//            }
//        }
//
//
//    /**
//     *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchOnlyWithImage=false
//     *  Comes with every item
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText ='', selectedFilter=FILTER_BY_NAME, searchOnlyWithImage=false and returns every image`() =
//        runTest {
//            searchedItems =
//                itemSearchHelper.search("", InventoryFilterOptionEnum.FILTER_BY_NAME.option, false)
//            assert(searchedItems.size == testRepository.getInventoryItems().size)
//        }
//
//    /**
//     *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchOnlyWithImage=true
//     *  Comes with every item with image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText ='', selectedFilter=FILTER_BY_PRICE, searchOnlyWithImage=true and returns every item with image`() =
//        runTest {
//            searchedItems =
//                itemSearchHelper.search("", InventoryFilterOptionEnum.FILTER_BY_PRICE.option, true)
//            searchedItems.forEach { each ->
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { assert(it.isNotEmpty()) }
//            }
//        }
//
//    /**
//     *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchOnlyWithImage=false
//     *  Comes with every item
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText ='', selectedFilter=FILTER_BY_PRICE, searchOnlyWithImage=false and returns every item`() =
//        runTest {
//            searchedItems =
//                itemSearchHelper.search("", InventoryFilterOptionEnum.FILTER_BY_PRICE.option, false)
//            assert(searchedItems.size == testRepository.getInventoryItems().size)
//        }
//
//    /**
//     *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchOnlyWithImage=true
//     *  Comes with every item with image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText ='', selectedFilter=NO_FILTER, searchOnlyWithImage=true and returns every item with image`() =
//        runTest {
//            searchedItems =
//                itemSearchHelper.search("", InventoryFilterOptionEnum.NO_FILTER.option, true)
//            searchedItems.forEach { each ->
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { assert(it.isNotEmpty()) }
//            }
//        }
//
//    /**
//     *  Pass searchText ="", selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchOnlyWithImage=false
//     *  Comes with every item with image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText ='', selectedFilter=NO_FILTER, searchOnlyWithImage=false return every item`() =
//        runTest {
//            searchedItems =
//                itemSearchHelper.search("", InventoryFilterOptionEnum.NO_FILTER.option, false)
//            assert(searchedItems.size == testRepository.getInventoryItems().size)
//        }
//
//
//    /**
//     * Test by passing searchText = "Item" and variations in selectedFilter, searchWithONlyImage
//     *
//     * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=false
//     * return all items with name or price column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText = 'Item' selectedFilter=null, searchWithOnlyImage=false and returns all items that matches 'Item' from name or price column`() =
//        runTest {
//            val searchText: String = "Item"
//
//            searchedItems = itemSearchHelper.search(searchText, null, false)
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=false
//     * return all items with name column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText ='Item' selectedFilter=FILTER_BY_NAME, searchWithOnlyImage=false and returns all items which matches 'Item' in name column`() =
//        runTest {
//            val searchText: String = "Item"
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_NAME.option, false
//            )
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText))
//            }
//        }
//
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=false
//     * return all items with price column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText ='Item' selectedFilter=FILTER_BY_PRICE, searchWithOnlyImage=false and returns all items which matches 'Item' in price column`() =
//        runTest {
//            val searchText: String = "Item"
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_PRICE.option, false
//            )
//            searchedItems.forEach { each ->
//                assert(each.price.contains(searchText))
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=false
//     * return all items with name or price column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText = 'Item' selectedFilter=NO_FILTER, searchWithOnlyImage=false and returns all items which matches 'Item' in name or price column`() =
//        runTest {
//            val searchText: String = "Item"
//            searchedItems =
//                itemSearchHelper.search(
//                    searchText,
//                    InventoryFilterOptionEnum.NO_FILTER.option,
//                    false
//                )
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=true
//     * return all items with name or price column matches "Item". Also, each should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText = 'Item' selectedFilter=null, searchWithOnlyImage=true and returns all items that matches 'Item' in name or price column which also have image`() =
//        runTest {
//            val searchText: String = "Item"
//            searchedItems = itemSearchHelper.search(searchText, null, true)
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=true
//     * return all items with name column matches "Item". Also, each should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText = 'Item' selectedFilter=FILTER_BY_NAME, searchWithOnlyImage=true and returns all items that matches 'Item' in column name also have image`() =
//        runTest {
//            val searchText: String = "Item"
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_NAME.option, true
//            )
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=true
//     * return all items with price column matches "Item". Also, each should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText = 'Item' selectedFilter=FILTER_BY_PRICE, searchWithOnlyImage=true and returns all items that matches 'Item' price column also have image in `() =
//        runTest {
//            val searchText: String = "Item"
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_PRICE.option, true
//            )
//            searchedItems.forEach { each ->
//                assert(each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=true
//     * return all items with name or price column matches "Item". Also, each Item should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass searchText='Item' selectedFitler=NO_FILTER, searchWithOnlyImage=true and returns all item that matches 'Item' in name or price column also have image`() =
//        runTest {
//            val searchText: String = "Item"
//            searchedItems =
//                itemSearchHelper.search(
//                    searchText,
//                    InventoryFilterOptionEnum.NO_FILTER.option,
//                    true
//                )
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//
//    /**
//     * Test by passing searchText= "1000" in items and variations in selectedFilter, searchWithONlyImage
//     *
//     * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=false
//     * return all items with name or price column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass  searchText = '1000' selectedFilter=null, searchWithOnlyImage=false and returns all items that matches '1000' in name or price column`() =
//        runTest {
//            val searchText: String = "1000"
//
//            searchedItems = itemSearchHelper.search(searchText, null, false)
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=false
//     * return all items with name column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass  searchText = '1000' selectedFilter=FILTER_BY_NAME, searchWithOnlyImage=false and returns all items that matches '1000' in name column`() =
//        runTest {
//            val searchText: String = "1000"
//
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_NAME.option, false
//            )
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText))
//            }
//
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=false
//     * return all items with price column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass  searchText = '1000' selectedFilter=FILTER_BY_PRICE, searchWithOnlyImage=false and returns all items that matches '1000' in price column`() =
//        runTest {
//            val searchText: String = "1000"
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_PRICE.option, false
//            )
//            searchedItems.forEach { each ->
//                assert(each.price.contains(searchText))
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=false
//     * return all items with name or price column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass  searchText = '1000' selectedFilter=NO_FILTER, searchWithOnlyImage=false and returns all items that matches '1000' in name or price column`() =
//        runTest {
//            val searchText: String = "1000"
//            searchedItems =
//                itemSearchHelper.search(
//                    searchText,
//                    InventoryFilterOptionEnum.NO_FILTER.option,
//                    false
//                )
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//            }
//
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=true
//     * return all items with name or price column matches "Item". Also, each should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass  searchText = '1000' selectedFilter=null, searchWithOnlyImage=true and returns all items that matches '1000' in name or price column also have image`() =
//        runTest {
//            val searchText: String = "1000"
//            searchedItems = itemSearchHelper.search(searchText, null, true)
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=true
//     * return all items with name column matches "Item". Also, each should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass  searchText = '1000' selectedFilter=FILTER_BY_NAME, searchWithOnlyImage=true and returns all items that matches '1000' in name column also have image`() =
//        runTest {
//            val searchText: String = "1000"
//
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_NAME.option, true
//            )
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=true
//     * return all items with price column matches "Item". Also, each should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass  searchText = '1000' selectedFilter=FILTER_BY_PRICE, searchWithOnlyImage=true and returns all items that matches '1000' in price column also have image`() =
//        runTest {
//            val searchText: String = "1000"
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_PRICE.option, true
//            )
//            searchedItems.forEach { each ->
//                assert(each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=true
//     * return all items with name or price column matches "Item". Also, each Item should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search() pass  searchText = '100' selectedFilter=NO_FILTER, searchWithOnlyImage=true and returns all items that matches '1000' in name or price column also have image`() =
//        runTest {
//            val searchText: String = "1000"
//            searchedItems =
//                itemSearchHelper.search(
//                    searchText,
//                    InventoryFilterOptionEnum.NO_FILTER.option,
//                    true
//                )
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//
//    /**
//     * Test by passing searchText= "5000" in items and variations in selectedFilter, searchWithONlyImage
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search PassSearchTextWithItemPrice=5000, selectedFilter=null,searchWithOnlyImage=false ReturnEveryItemThatMatchesSearchText`() =
//        runTest {
//            val searchText: String = "5000"
//            /**
//             * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=false
//             * return all items with name or price column matches "Item"
//             * */
//
//            searchedItems = itemSearchHelper.search(searchText, null, false)
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//            }
//
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=false
//     * return all items with name column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search PassSearchTextWithItemPrice=5000, selectedFilter=FILTER_BY_NAME,searchWithOnlyImage=false ReturnEveryItemThatMatchesSearchText`() =
//        runTest {
//            val searchText: String = "5000"
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_NAME.option, false
//            )
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText))
//            }
//
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=false
//     * return all items with price column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search PassSearchTextWithItemPrice=Item, selectedFilter=FILTER_BY_PRICE,searchWithOnlyImage=false ReturnEveryItemThatMatchesSearchText`() =
//        runTest {
//            val searchText: String = "Item"
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_PRICE.option, false
//            )
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText))
//            }
//        }
//
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=false
//     * return all items with name or price column matches "Item"
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search PassSearchTextWithItemPrice=Item, selectedFilter=NO_FILTER,searchWithOnlyImage=false ReturnEveryItemThatMatchesSearchText`() =
//        runTest {
//            val searchText: String = "Item"
//
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.NO_FILTER.option, false
//            )
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//            }
//        }
//
//
//    /**
//     * Pass searchText = "Item" selectedFilter=null, searchWithOnlyImage=true
//     * return all items with name or price column matches "Item". Also, each should have image
//     * */
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search PassSearchTextWithItemPrice=Item, selectedFilter=null,searchWithOnlyImage=true ReturnEveryItemThatMatchesSearchText`() =
//        runTest {
//            val searchText: String = "Item"
//
//            searchedItems = itemSearchHelper.search(searchText, null, true)
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_NAME, searchWithOnlyImage=true
//     * return all items with name column matches "Item". Also, each should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search PassSearchTextWithItemPrice=Item, selectedFilter=FILTER_BY_NAME,searchWithOnlyImage=true ReturnEveryItemThatMatchesSearchText`() =
//        runTest {
//            val searchText: String = "Item"
//
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_NAME.option, true
//            )
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.FILTER_BY_PRICE, searchWithOnlyImage=true
//     * return all items with price column matches "Item". Also, each should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search PassSearchTextWithItemPrice=Item, selectedFilter=FILTER_BY_PRICE,searchWithOnlyImage=true ReturnEveryItemThatMatchesSearchText`() =
//        runTest {
//            val searchText: String = "Item"
//
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.FILTER_BY_PRICE.option, true
//            )
//            searchedItems.forEach { each ->
//                assert(each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }
//
//    /**
//     * Pass searchText = "Item" selectedFilter=InventoryFilterOptionEnum.NO_FILTER, searchWithOnlyImage=true
//     * return all items with name or price column matches "Item". Also, each Item should have image
//     * */
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `search PassSearchTextWithItemPrice=Item, selectedFilter=NO_FILTER,searchWithOnlyImage=true ReturnEveryItemThatMatchesSearchText`() =
//        runTest {
//            val searchText: String = "Item"
//
//            searchedItems = itemSearchHelper.search(
//                searchText, InventoryFilterOptionEnum.NO_FILTER.option, true
//            )
//
//            searchedItems.forEach { each ->
//                assert(each.name.contains(searchText) || each.price.contains(searchText))
//                assert(each.imageUrl != null)
//                each.imageUrl?.let { image ->
//                    assert(image.isNotEmpty())
//                }
//            }
//        }

}