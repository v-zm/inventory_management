package com.vivek.inventorymanagement.data.repository

import com.vivek.inventorymanagement.data.api.InventoryHttpClient
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemDto
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemListDto
import com.vivek.inventorymanagement.data.api.services.InventoryApiService
import com.vivek.inventorymanagement.data.database.inventory.InventoryDatabaseImp
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val inventoryDb: InventoryDatabaseImp, val coroutineDispatcher: CoroutineDispatcher
) : IInventoryRepository(inventory = InventoryHttpClient()) {

    override suspend fun getInventoryItems(): List<Item>? {
        return withContext(coroutineDispatcher) {
            var resultItems: List<Item>? = null
            val itemEntities: List<ItemEntity> =
                inventoryDb.getInventoryDatabase().itemDao().getAll()
            if (itemEntities.isNotEmpty()) {
                resultItems = itemEntities.map { each ->
                    Item.getItemFromItemEntity(each)
                }
            } else {
                val service: InventoryApiService =
                    inventory.getBaseAdapter().create(InventoryApiService::class.java)
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
                            inventoryDb.getInventoryDatabase().itemDao()
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
        return withContext(coroutineDispatcher) {
            var resultItems: List<Item> = ArrayList<Item>()

            val itemEntites: List<ItemEntity> = when (searchType) {
                InventoryFilterOptionEnum.FILTER_BY_NAME -> if (searchOnlyWithImage) inventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByName(searchText) else inventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByNameAndImage(searchText)
                InventoryFilterOptionEnum.FILTER_BY_PRICE -> if (searchOnlyWithImage) inventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByPrice(searchText) else inventoryDb.getInventoryDatabase()
                    .itemDao().getItemsByPriceAndImage(searchText)
                InventoryFilterOptionEnum.NO_FILTER -> if (searchOnlyWithImage) inventoryDb.getInventoryDatabase()
                    .itemDao()
                    .getItemsByNameOrPrice(searchText) else inventoryDb.getInventoryDatabase()
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