package com.vivek.inventorymanagement.data.api.services

import com.vivek.inventorymanagement.data.api.config.InventoryApiConfig
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemListDto
import retrofit2.Call
import retrofit2.http.GET

interface InventoryApiService {
    @GET(InventoryApiConfig.GET_INVENTORY_ITEMS_URL)
    fun getInventoryList(): Call<InventoryItemListDto>
}