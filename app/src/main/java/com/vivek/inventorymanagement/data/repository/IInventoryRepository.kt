package com.vivek.inventorymanagement.data.repository

import com.vivek.inventorymanagement.data.api.InventoryHttpClient
import com.vivek.inventorymanagement.features.inventory.model.Item

abstract class IInventoryRepository(val inventory: InventoryHttpClient) {
    abstract suspend fun getInventoryItems(): List<Item>?
}