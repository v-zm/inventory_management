package com.vivek.inventorymanagement.features.inventory.view.helper

import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import javax.inject.Inject

class ItemSearchHelper @Inject constructor(
    val inventoryRepository: IInventoryRepository,
) {

    suspend fun search(
        searchText: String,
        selectedFilter: String?,
        searchOnlyWithImage: Boolean,

        ): List<Item> {
        val filterOption: InventoryFilterOptionEnum? = selectedFilter?.let { tempFilter ->
            InventoryFilterOptionEnum.getInvInventoryFilterOptionEnumByName(
                tempFilter
            )
        }
        val items: List<Item> = performDBSearch(searchText, filterOption, searchOnlyWithImage)
        return items
    }

    suspend fun performDBSearch(
        searchText: String,
        filterOption: InventoryFilterOptionEnum?,
        searchOnlyWithImage: Boolean,
    ): List<Item> {
        return inventoryRepository.getInventorySearchItems(
            searchText,
            filterOption ?: InventoryFilterOptionEnum.FILTER_BY_NAME,
            searchOnlyWithImage
        )

    }
}