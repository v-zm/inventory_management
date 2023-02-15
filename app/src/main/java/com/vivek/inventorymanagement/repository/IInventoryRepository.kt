package com.vivek.inventorymanagement.repository

import com.vivek.inventorymanagement.api.InventoryHttpClient
import com.vivek.inventorymanagement.dtos.InventoryItemListDto

abstract class IInventoryRepository(val inventory: InventoryHttpClient) {
    abstract suspend fun getInventoryItems(): InventoryItemListDto?
}