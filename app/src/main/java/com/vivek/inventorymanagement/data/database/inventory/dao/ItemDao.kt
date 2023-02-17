package com.vivek.inventorymanagement.data.database.inventory.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): List<ItemEntity>

    @Query("SELECT * FROM item where name LIKE '%' || :searchText || '%'")
    fun getItemsByName(searchText: String): List<ItemEntity>

    @Query("SELECT * FROM item where name LIKE '%' || :searchText || '%' AND image IS NOT NULL")
    fun getItemsByNameAndImage(searchText: String): List<ItemEntity>

    @Query("SELECT * FROM item where price LIKE '%' || :price || '%'")
    fun getItemsByPrice(price: String): List<ItemEntity>

    @Query("SELECT * FROM item where price LIKE '%' || :price || '%' AND image IS NOT NULL")
    fun getItemsByPriceAndImage(price: String): List<ItemEntity>

    @Query("SELECT * FROM item where name LIKE '%' || :searchText || '%' OR  price LIKE '%' || :searchText || '%'")
    fun getItemsByNameOrPrice(searchText: String): List<ItemEntity>
    @Query("SELECT * FROM item where name LIKE '%' || :searchText || '%' OR  price LIKE '%' || :searchText || '%' AND image IS NOT NULL")
    fun getItemsByNameOrPriceAndImage(searchText: String): List<ItemEntity>

    @Insert
    fun insertAll(items: List<ItemEntity>)
}