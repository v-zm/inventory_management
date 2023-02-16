package com.vivek.inventorymanagement.data.api.dtos

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemsDto(
    @JsonProperty("items")
    val items: List<InventoryItemDto>
) : Parcelable