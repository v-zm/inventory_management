package com.vivek.inventorymanagement

import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.data.repository.InventoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {
    companion object {

        /** [getDispatch] provides [CoroutineDispatcher] to overall module */
        @Provides
        @Singleton
        fun getDispatch(): CoroutineDispatcher {
            return Dispatchers.IO
        }
    }

    /** [bindInventoryRepository] provides [InventoryRepository] to overall module */
    @Binds
    @Singleton
    abstract fun bindInventoryRepository(inventoryRepository: InventoryRepository): IInventoryRepository


}