package com.githukudenis.androidtesting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.androidtesting.data.local.ShoppingItem
import com.githukudenis.androidtesting.data.remote.ImageResponse
import com.githukudenis.androidtesting.other.Event
import com.githukudenis.androidtesting.other.Resource
import com.githukudenis.androidtesting.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
): ViewModel() {

    val shoppingItems = repository.observeShoppingItems()

    val totalPrice = repository.observeTotalPrice()


    private var _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> get() = _images

    private var _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: LiveData<String> get() = _currentImageUrl


    private var _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> get() = _insertShoppingItemStatus

    fun setCurrentImageUrl(url: String) = _currentImageUrl.postValue(url)

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch { repository.deleteShoppingItem(shoppingItem) }
    }

    fun insertShoppingItemToDb(shoppingItem: ShoppingItem) {
        viewModelScope.launch { repository.insertShoppingItem(shoppingItem) }
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {

    }

    fun searchForImage(query: String) {

    }


}
