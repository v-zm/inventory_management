package com.vivek.inventorymanagement.data.api.services

import com.vivek.inventorymanagement.data.api.dtos.InventoryItemDto
import com.vivek.inventorymanagement.data.api.dtos.InventoryItemListDto
import retrofit2.Call
import retrofit2.http.GET

public interface InventoryApiService {
    @GET("v3/995ce2a0-1daf-4993-915f-8c198f3f752c")
    fun getInventoryList(): Call<InventoryItemListDto>
}