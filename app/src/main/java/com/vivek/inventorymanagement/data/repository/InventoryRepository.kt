package com.vivek.inventorymanagement.data.repository


import android.content.Context
import com.vivek.inventorymanagement.data.api.clients.IHttpClient
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemDto
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemListDto
import com.vivek.inventorymanagement.data.api.services.InventoryApiService
import com.vivek.inventorymanagement.data.database.inventory.IInventoryDatabase
import com.vivek.inventorymanagement.data.database.inventory.InventoryDatabaseImp
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity
import com.vivek.inventorymanagement.data.util.ApiUtility
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val mInventoryDb: InventoryDatabaseImp,
    private val mCoroutineDispatcher: CoroutineDispatcher,
    private val mHttpClient: IHttpClient,
    @ApplicationContext private val mApplicationContext: Context
) : IInventoryRepository() {

    /**
     * [getInventoryItems] checks data in [IInventoryDatabase]
     * */
    override suspend fun getInventoryItems(): List<Item>? {
        return withContext(mCoroutineDispatcher) {
            var resultItems: List<Item>? = null
            val itemEntities: List<ItemEntity> =
                mInventoryDb.getInventoryDatabase().itemDao().getAll()
            if (itemEntities.isNotEmpty()) {
                resultItems = itemEntities.map { each ->
                    Item.getItemFromItemEntity(each)
                }
            } else {
                if (!ApiUtility.isInternetConnected(mApplicationContext)) {
                    return@withContext null
                }
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
                            mInventoryDb.getInventoryDatabase().itemDao()
                                .insertAll(resultItemEntities)
                        }
                    }
                }
            }

            resultItems
        }
    }

    override suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum, searchOnlyWithImage: Boolean
    ): List<Item> {
        return withContext(mCoroutineDispatcher) {
            var resultItems: List<Item> = ArrayList<Item>()

            val itemEntites: List<ItemEntity> = when (searchType) {
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

            itemEntites.let { tempItemEntites ->
                resultItems = tempItemEntites.map { each ->
                    Item.getItemFromItemEntity(each)
                }
            }
            resultItems
        }
    }


}