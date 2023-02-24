package com.vivek.inventorymanagement.setup.repository

import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item

class FakeRepository(val itemCount: Int) : IInventoryRepository() {
    /**
     * Item(name=Item0, price=0, extra=Shipping is available, imageUrl=https://hamcrest.org/images/logo.jpg)
    Item(name=Item1, price=1000, extra=Shipping not available, imageUrl=)
    Item(name=Item2, price=2000, extra=Shipping is available, imageUrl=)
    Item(name=Item3, price=3000, extra=Shipping not available, imageUrl=https://hamcrest.org/images/logo.jpg)
    Item(name=Item4, price=4000, extra=Shipping is available, imageUrl=)
    Item(name=Item5, price=5000, extra=Shipping not available, imageUrl=)
    Item(name=Item6, price=6000, extra=Shipping is available, imageUrl=https://hamcrest.org/images/logo.jpg)
    Item(name=Item7, price=7000, extra=Shipping not available, imageUrl=)
    Item(name=Item8, price=8000, extra=Shipping is available, imageUrl=)
    Item(name=Item9, price=9000, extra=Shipping not available, imageUrl=https://hamcrest.org/images/logo.jpg)
    Item(name=Item10, price=0, extra=Shipping is available, imageUrl=)
    Item(name=Item11, price=1000, extra=Shipping not available, imageUrl=)
    Item(name=Item12, price=2000, extra=Shipping is available, imageUrl=https://hamcrest.org/images/logo.jpg)
    Item(name=Item13, price=3000, extra=Shipping not available, imageUrl=)
    Item(name=Item14, price=4000, extra=Shipping is available, imageUrl=)
     * */
    private val items: List<Item> = List(itemCount) { index ->
        Item(
            name = "Item$index",
            price = "${(index % 10) * 1000}",
            extra = if (index % 2 == 0) "Shipping is available" else "Shipping not available",
            imageUrl = if (index % 3 == 0) "https://hamcrest.org/images/logo.jpg" else ""
        )
    }

    override suspend fun getInventoryItems(): List<Item> {
        for (item in items) {
            println(item.toString())
        }
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