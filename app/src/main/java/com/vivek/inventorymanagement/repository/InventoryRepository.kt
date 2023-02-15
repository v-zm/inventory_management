package com.vivek.inventorymanagement.repository

import com.vivek.inventorymanagement.api.InventoryApiService
import com.vivek.inventorymanagement.api.InventoryHttpClient
import com.vivek.inventorymanagement.dtos.InventoryItemListDto

class InventoryRepository : IInventoryRepository(inventory = InventoryHttpClient()) {

    override suspend fun getInventoryItems(): InventoryItemListDto? {
        val service: InventoryApiService =
            inventory.getBaseAdapter().create(InventoryApiService::class.java)
        val data: InventoryItemListDto? = service.getInventoryList().execute().body()
        return data
    }
}