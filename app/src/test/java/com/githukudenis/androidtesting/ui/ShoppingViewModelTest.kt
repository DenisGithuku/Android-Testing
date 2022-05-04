package com.githukudenis.androidtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.githukudenis.androidtesting.other.Constants
import com.githukudenis.androidtesting.other.Status
import com.githukudenis.androidtesting.repository.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import getOrAwaitValueTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    private lateinit var shoppingViewModel: ShoppingViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        shoppingViewModel = ShoppingViewModel(FakeShoppingRepository())

        /**
         * tune the coroutine to run on the standard dispatcher on the main thread
         */
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `insert a shopping item with empty fields returns error`() {
        shoppingViewModel.insertShoppingItem("name", "", "3.0")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name returns error`() {
        val nameString = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem(nameString, "4.5", "3.0")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price returns error`() {
        val priceString = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem("name", "33", priceString)
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount returns error`() {
        shoppingViewModel.insertShoppingItem("name", "4444444444444444", "3.0")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, returns success`() {
        shoppingViewModel.insertShoppingItem("name", "4", "3.0")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `current image url is empty after inserting shopping item into db`() {
        shoppingViewModel.insertShoppingItem("name", "4", "3.0")
        val currentImageUrl = shoppingViewModel.currentImageUrl.getOrAwaitValueTest()

        assertThat(currentImageUrl).isEmpty()

    }
}
