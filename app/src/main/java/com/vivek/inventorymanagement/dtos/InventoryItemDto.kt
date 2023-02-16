package com.vivek.inventorymanagement.dtos

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryItemDto(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("price")
    val price: String,
    @JsonProperty("extra")
    val extra: String?,
    @JsonProperty("image")
    val image: String
) : Parcelable