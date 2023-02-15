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
    val data: ItemsDto
) : Parcelable

