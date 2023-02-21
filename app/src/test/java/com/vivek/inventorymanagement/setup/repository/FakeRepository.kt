package com.vivek.inventorymanagement.setup.repository

import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item

class FakeRepository : IInventoryRepository() {
    val items: List<Item> = List(100) { index ->
        Item(
            name = "Item1",
            price = "${(index % 10) * 1000}",
            extra = if (index % 2 == 0) "Shipping is available" else "Shipping not availale",
            imageUrl = if (index % 3 == 0) "https://hamcrest.org/images/logo.jpg" else ""
        )
    }

    override suspend fun getInventoryItems(): List<Item> {
        return items
    }

    override suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum, searchOnlyWithImage: Boolean
    ): List<Item> {
        return when (searchType) {
            InventoryFilterOptionEnum.FILTER_BY_NAME -> {
                items.filter { each ->
                    each.name.contains(searchText) && (if (searchOnlyWithImage) (each.imageUrl?.isNotEmpty()
                        ?: false) else {
                        true
                    })

                }
            }
            InventoryFilterOptionEnum.FILTER_BY_PRICE -> {
                items.filter { each ->
                    each.price.contains(searchText) && (if (searchOnlyWithImage) (each.imageUrl?.isNotEmpty()
                        ?: false) else {
                        true
                    })
                }

            }
            InventoryFilterOptionEnum.NO_FILTER -> {
                items.filter { each ->
                    (each.name.contains(searchText) || each.price.contains(searchText)) && (if (searchOnlyWithImage) (each.imageUrl?.isNotEmpty()
                        ?: false) else {
                        true
                    })
                }
            }
        }


    }
}