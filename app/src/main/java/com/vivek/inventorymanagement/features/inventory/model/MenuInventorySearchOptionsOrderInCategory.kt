package com.vivek.inventorymanagement.features.inventory.model

sealed class MenuInventorySearchOptionsOrderInCategory {
    companion object {
        val filterCategory:Int=1
        val selectionCategory:Int=2
    }
}