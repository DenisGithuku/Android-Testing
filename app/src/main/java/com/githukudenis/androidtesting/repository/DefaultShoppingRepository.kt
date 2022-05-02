package com.githukudenis.androidtesting.repository

import androidx.lifecycle.LiveData
import com.githukudenis.androidtesting.data.local.ShoppingDao
import com.githukudenis.androidtesting.data.local.ShoppingItem
import com.githukudenis.androidtesting.data.remote.ImageResponse
import com.githukudenis.androidtesting.data.remote.PixaBayApi
import com.githukudenis.androidtesting.other.Resource
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixaBayApi: PixaBayApi
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchImage(query: String): Resource<ImageResponse> {
        return try {
            Resource.loading(null)
            val response = pixaBayApi.searchForImage(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(null,"An unknown error occurred")
            } else {
                Resource.error(null,"An unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.error(null,"Couldn't reach the server check your internet connection")
        }
    }
}
