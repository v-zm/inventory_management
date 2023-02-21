package com.vivek.inventorymanagement.setup.repository

import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item

class FakeRepository : IInventoryRepository() {

    var failEnabled: Boolean = false
    override suspend fun getInventoryItems(): List<Item> {
//        if (failEnabled)
//            return null


        return listOf(
            Item(
                "Item 1",
                "100",
                "Same day shipping",
                "https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png"
            )
        )
    }

    override suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum, searchOnlyWithImage: Boolean
    ): List<Item> {
        TODO("Not yet implemented")
    }

    fun getStaticListOfItem(): List<Item> {
        return listOf(
            Item(
                "Item 1",
                "100",
                "Same day shipping",
                "https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png"
            )
        )
    }

    fun getOneItem(): Item {
        return Item(
            "Item 1",
            "100",
            "Same day shipping",
            "https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png"
        )

    }
}