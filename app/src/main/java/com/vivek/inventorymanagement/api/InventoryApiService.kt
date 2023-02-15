package com.vivek.inventorymanagement.api

import com.vivek.inventorymanagement.dtos.InventoryItemDto
import com.vivek.inventorymanagement.dtos.InventoryItemListDto
import retrofit2.Call
import retrofit2.http.GET

public interface InventoryApiService {
    @GET("v3/b6a30bb0-140f-4966-8608-1dc35fa1fadc")
    fun getInventoryList(): Call<InventoryItemListDto>
}