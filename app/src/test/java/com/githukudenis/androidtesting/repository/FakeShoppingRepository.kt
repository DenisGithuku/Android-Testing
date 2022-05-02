package com.githukudenis.androidtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githukudenis.androidtesting.data.local.ShoppingItem
import com.githukudenis.androidtesting.data.remote.ImageResponse
import com.githukudenis.androidtesting.other.Resource

class FakeShoppingRepository : ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()
    private var shouldReturnNetworkError: Boolean = false

    fun setShouldReturnNetWorkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observeTotalPrice()
    }

    override suspend fun searchImage(query: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error(null, "Error")
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}
