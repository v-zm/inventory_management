package com.vivek.inventorymanagement.data.repository

import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IInventoryRepository {
    suspend fun getInventoryItems(): List<Item>?
    suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum,
        searchOnlyWithImage: Boolean = false
    ): List<Item>

    fun getLatestQueriedItems(): StateFlow<InventoryViewState>
    fun getLatestQueriedItems1(): StateFlow<List<Item>>

    fun inventorySearch(
            searchText: String, searchType: InventoryFilterOptionEnum,
        searchOnlyWithImage: Boolean = false
    ): Flow<InventoryViewState>
}