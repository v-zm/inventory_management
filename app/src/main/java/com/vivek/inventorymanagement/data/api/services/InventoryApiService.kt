package com.vivek.inventorymanagement.data.api.services

import com.vivek.inventorymanagement.data.api.config.InventoryApiConfig
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemListDto
import retrofit2.Response
import retrofit2.http.GET

interface InventoryApiService {
    @GET(InventoryApiConfig.GET_INVENTORY_ITEMS_URL)
    suspend fun getInventoryList(): Response<InventoryItemListDto>
}