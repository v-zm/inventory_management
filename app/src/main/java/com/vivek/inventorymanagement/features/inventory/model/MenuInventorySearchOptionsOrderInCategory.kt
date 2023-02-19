package com.vivek.inventorymanagement.features.inventory.model

sealed class MenuInventorySearchOptionsOrderInCategory {
    companion object {
        const val filterCategory:Int=1
        const val selectionCategory:Int=2
    }
}