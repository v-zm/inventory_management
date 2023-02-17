package com.vivek.inventorymanagement.setup.repository

import com.vivek.inventorymanagement.data.api.InventoryHttpClient
import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item

class FakeRepository : IInventoryRepository(InventoryHttpClient()) {

//    var failEnabled: Boolean = false
    override suspend fun getInventoryItems(): List<Item>? {
//        return null
        val list: ArrayList<Item> = ArrayList()
        list.add(Item("", "", "", ""))
        return list
    }

    override suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum, searchOnlyWithImage: Boolean
    ): List<Item> {
        TODO("Not yet implemented")
    }
}