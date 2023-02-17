package com.vivek.inventorymanagement.features.inventory.enums

enum class InventoryFilterOptionEnum(val option: String) {
    FILTER_BY_NAME("Name"), FILTER_BY_PRICE("Price"), NO_FILTER("No Filter");

    companion object {

        fun getInvInventoryFilterOptionEnumByName(option: String): InventoryFilterOptionEnum? {
            return enumValues<InventoryFilterOptionEnum>().firstOrNull { enumValue ->
                enumValue.option == option
            }
        }
    }


}