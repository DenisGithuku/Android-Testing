package com.githukudenis.androidtesting.repository

import androidx.lifecycle.LiveData
import com.githukudenis.androidtesting.data.local.ShoppingItem
import com.githukudenis.androidtesting.data.remote.ImageResponse
import com.githukudenis.androidtesting.other.Resource

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchImage(query: String): Resource<ImageResponse>
}
