package com.vivek.inventorymanagement.data.repository


import com.vivek.inventorymanagement.data.api.clients.IHttpClient
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemDto
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemListDto
import com.vivek.inventorymanagement.data.api.exception.NetworkException
import com.vivek.inventorymanagement.data.api.services.InventoryApiService
import com.vivek.inventorymanagement.data.database.inventory.IInventoryDatabase
import com.vivek.inventorymanagement.data.database.inventory.InventoryDatabaseImp
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity
import com.vivek.inventorymanagement.data.util.DateTimeUtility
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val mInventoryDb: InventoryDatabaseImp,
    private val mCoroutineDispatcher: CoroutineDispatcher,
    private val mHttpClient: IHttpClient,
) : IInventoryRepository {

    private val _currentQueriedItems: MutableStateFlow<InventoryViewState> by lazy {
        MutableStateFlow<InventoryViewState>(InventoryViewState.Success(listOf()))
    }
    private val _inventoryItems: MutableStateFlow<List<Item>> by lazy {
        MutableStateFlow<List<Item>>(listOf())
    }
    val inventoryItemState: StateFlow<List<Item>> get() = _inventoryItems

    /**
     * [getInventoryItems] checks data in [IInventoryDatabase]
     * */
    override suspend fun getInventoryItems(): List<Item>? {
        return withContext(mCoroutineDispatcher) {

            var resultItems: List<Item>? = getItemsFromDBInsertedInLastOneDay()

            if (resultItems == null || resultItems.isEmpty()) {
                resultItems = getItemsFromApi()
            }
            updateCurrentQueriedItemFlow(resultItems)
            resultItems
        }
    }


    /**
     * Get Items from DB
     * then check if they are not older than one day
     * If they are older than one day then discard result and return null
     * */
    private suspend fun getItemsFromDBInsertedInLastOneDay(): List<Item>? {
        var resultItems: List<Item>? = null
        try {
            val itemEntities: List<ItemEntity> =
                mInventoryDb.getInventoryDatabase().itemDao().getAll()

            if (itemEntities.isNotEmpty() && !DateTimeUtility.hasOneDayPassed(itemEntities.first().createdAt)) {
                resultItems = itemEntities.map { each ->
                    Item.getItemFromItemEntity(each)
                }
            }
        } catch (_: Exception) {
            // Download new data
        }
        return resultItems
    }

    /**
     * Get Items from API
     * then insert Items in DB
     * then return items
     * */
    private fun getItemsFromApi(): List<Item>? {
        var resultItems: List<Item>? = null
        try {
            val service: InventoryApiService =
                mHttpClient.getHttpClient().create(InventoryApiService::class.java)
            val data: Response<InventoryItemListDto>? = service.getInventoryList().execute()

            if (data?.isSuccessful == true) {
                val itemList: List<InventoryItemDto>? = data.body()?.data?.items

                itemList?.let { tempItemList ->
                    resultItems = tempItemList.map { each ->
                        Item.getItemFromItemDto(each)
                    }

                    resultItems?.let { tempResultItems ->
                        val resultItemEntities: List<ItemEntity> = tempResultItems.map { each ->
                            ItemEntity.getItemEntity(each)
                        }
                        mInventoryDb.getInventoryDatabase().itemDao().deleteAll()
                        mInventoryDb.getInventoryDatabase().itemDao().insertAll(resultItemEntities)
                    }
                }
            }
        } catch (networkException: NetworkException) {
            /*
            Handle Network exception
             */
            throw networkException
        } catch (exception: java.lang.Exception) {
            /*
           Handle Unknown exception
            */
            throw exception
        }
        return resultItems
    }


    override suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum, searchOnlyWithImage: Boolean
    ): List<Item> {
        return withContext(mCoroutineDispatcher) {
            var resultItems: List<Item> = ArrayList<Item>()

            val itemEntities: List<ItemEntity> = when (searchType) {
                InventoryFilterOptionEnum.FILTER_BY_NAME -> if (searchOnlyWithImage) mInventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByName(searchText) else mInventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByNameAndImage(searchText)
                InventoryFilterOptionEnum.FILTER_BY_PRICE -> if (searchOnlyWithImage) mInventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByPrice(searchText) else mInventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByPriceAndImage(searchText)
                InventoryFilterOptionEnum.NO_FILTER -> if (searchOnlyWithImage) mInventoryDb.getInventoryDatabase()
                    .itemDao()
                    .getItemsByNameOrPrice(searchText) else mInventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByNameOrPriceAndImage(searchText)
            }

            itemEntities.let { tempItemEntities ->
                resultItems = tempItemEntities.map { each ->
                    Item.getItemFromItemEntity(each)
                }
            }
            updateCurrentQueriedItemFlow(resultItems)
            resultItems
        }
    }

    override fun getLatestQueriedItems(): StateFlow<InventoryViewState> {
        return _currentQueriedItems.asStateFlow()
    }

    override fun getLatestQueriedItems1(): StateFlow<List<Item>> {
        return inventoryItemState
    }

    override fun inventorySearch(
        searchText: String, searchType: InventoryFilterOptionEnum, searchOnlyWithImage: Boolean
    ): Flow<InventoryViewState> = flow {
        var resultItems: List<Item>? = listOf()
        emit(InventoryViewState.Loading(true))
        try {
            if (searchText.isEmpty()) {
                resultItems = getItemsFromDBInsertedInLastOneDay()

                if (resultItems == null || resultItems.isEmpty()) {
                    resultItems = getItemsFromApi()
                }
            } else {
                resultItems = getInventorySearchItems(searchText, searchType, searchOnlyWithImage)
            }
            updateCurrentQueriedItemFlow(resultItems)
            emit(InventoryViewState.Success(resultItems ?: listOf()))
        } catch (e: java.lang.Exception) {
            emit(InventoryViewState.Error(e))
        }

        emit(InventoryViewState.Loading(false))
    }

    private fun updateCurrentQueriedItemFlow(items: List<Item>?) {
        _currentQueriedItems.value = InventoryViewState.Success(items ?: listOf())
        _inventoryItems.value = items ?: listOf()
    }


}