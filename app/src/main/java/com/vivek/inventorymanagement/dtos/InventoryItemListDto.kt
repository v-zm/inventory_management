package com.vivek.inventorymanagement.dtos

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryItemListDto(
    @JsonProperty("status")
    val status: String,
    @JsonProperty("error")
    val error: String?,
    @JsonProperty("data")
    val data: Items
) : Parcelable

@Parcelize
data class Items(
    @JsonProperty("items")
    val items: List<InventoryItemDto>
) : Parcelable