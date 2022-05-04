package com.githukudenis.androidtesting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.androidtesting.data.local.ShoppingItem
import com.githukudenis.androidtesting.data.remote.ImageResponse
import com.githukudenis.androidtesting.other.Constants
import com.githukudenis.androidtesting.other.Event
import com.githukudenis.androidtesting.other.Resource
import com.githukudenis.androidtesting.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = repository.observeShoppingItems()

    val totalPrice = repository.observeTotalPrice()


    private var _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> get() = _images

    private var _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: LiveData<String> get() = _currentImageUrl


    private var _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> get() = _insertShoppingItemStatus

    private fun setCurrentImageUrl(url: String) = _currentImageUrl.postValue(url)

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch { repository.deleteShoppingItem(shoppingItem) }
    }

    private fun insertShoppingItemToDb(shoppingItem: ShoppingItem) {
        viewModelScope.launch { repository.insertShoppingItem(shoppingItem) }
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(null, "The fields must not be empty")
                )
            )
            return
        }

        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        null,
                        "The name of the item must not exceed ${Constants.MAX_NAME_LENGTH}"
                    )
                )
            )
            return
        }

        if (priceString.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        null,
                        "The price must not exceed ${Constants.MAX_PRICE_LENGTH}"
                    )
                )
            )
            return
        }
        val amount = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        null,
                        "Please enter a valid amount"
                    )
                )
            )
            return
        }
        val shoppingItem =
            ShoppingItem(0, name, amount, priceString.toFloat(), _currentImageUrl.value ?: "")
        insertShoppingItemToDb(shoppingItem)
        setCurrentImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))

    }

    fun searchForImage(query: String) {
        if (query.isEmpty()) {
            return
        }
        _images.value = (Event(Resource.loading(null)))
        viewModelScope.launch {
            val response = repository.searchImage(query)
            _images.value = Event(response)
        }
    }


}
