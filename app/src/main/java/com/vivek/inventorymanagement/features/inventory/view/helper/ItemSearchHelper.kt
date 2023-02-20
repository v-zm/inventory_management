package com.vivek.inventorymanagement.features.inventory.view.helper

import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import javax.inject.Inject

class ItemSearchHelper @Inject constructor(
    private val mInventoryRepository: IInventoryRepository,
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
        return performDbSearch(searchText, filterOption, searchOnlyWithImage)
    }

    private suspend fun performDbSearch(
        searchText: String,
        filterOption: InventoryFilterOptionEnum?,
        searchOnlyWithImage: Boolean,
    ): List<Item> {
        return mInventoryRepository.getInventorySearchItems(
            searchText,
            filterOption ?: InventoryFilterOptionEnum.FILTER_BY_NAME,
            searchOnlyWithImage
        )
    }
}