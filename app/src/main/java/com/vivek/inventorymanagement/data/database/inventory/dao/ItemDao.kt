package com.vivek.inventorymanagement.data.database.inventory.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    suspend fun getAll(): List<ItemEntity>

    @Query("SELECT * FROM item where name LIKE '%' || :searchText || '%'")
    suspend fun getItemsByName(searchText: String): List<ItemEntity>

    @Query("SELECT * FROM item where name LIKE '%' || :searchText || '%' AND image IS NOT NULL")
    suspend fun getItemsByNameAndImage(searchText: String): List<ItemEntity>

    @Query("SELECT * FROM item where price LIKE '%' || :price || '%'")
    suspend fun getItemsByPrice(price: String): List<ItemEntity>

    @Query("SELECT * FROM item where price LIKE '%' || :price || '%' AND image IS NOT NULL")
    suspend fun getItemsByPriceAndImage(price: String): List<ItemEntity>

    @Query("SELECT * FROM item where name LIKE '%' || :searchText || '%' OR  price LIKE '%' || :searchText || '%'")
    suspend fun getItemsByNameOrPrice(searchText: String): List<ItemEntity>

    @Query("SELECT * FROM item where (name LIKE '%' || :searchText || '%' OR  price LIKE '%' || :searchText || '%') AND image IS NOT NULL")
    suspend fun getItemsByNameOrPriceAndImage(searchText: String): List<ItemEntity>

    @Insert
    suspend fun insertAll(items: List<ItemEntity>)

    @Query("DELETE FROM item")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(item: ItemEntity)
}