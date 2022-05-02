package com.githukudenis.androidtesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    private lateinit var shoppingItemDatabase: ShoppingItemDatabase
    private lateinit var shoppingDao: ShoppingDao

    @get:Rule
    var hiltExecutorRule =  InstantTaskExecutorRule()

    @Before
    fun setup() {
        shoppingItemDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        shoppingDao = shoppingItemDatabase.shoppingItemDao()
    }

    @After
    fun tearDown() {
        shoppingItemDatabase.close()
    }

    @Test
    fun insertShoppingItemTest() = runTest {
        val shoppingItem = ShoppingItem( id =0, name = "banana", amount = 4, price = 45.6f, imageUrl = "")
        shoppingDao.insertShoppingItem(shoppingItem)

        val shoppingItems = shoppingDao.observeShoppingItems().getOrAwaitValue()
        assertThat(shoppingItems).contains(shoppingItem)
    }
    @Test
    fun deleteShoppingItemTest() = runTest {
        val shoppingItem = ShoppingItem( id =0, name = "banana", amount = 4, price = 45.6f, imageUrl = "")
        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.deleteShoppingItem(shoppingItem)
        val shoppingItems = shoppingDao.observeShoppingItems().getOrAwaitValue()
        assertThat(shoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceTest() = runTest {
        val shoppingItem1 = ShoppingItem(id = 0, name = "cake", amount = 2, price = 34.5f, imageUrl = "")
        val shoppingItem2 = ShoppingItem(id = 1, name = "banana", amount = 4, price = 23.5f, imageUrl = "")
        val shoppingItem3 = ShoppingItem(id = 2, name = "rice", amount = 3, price = 14.5f, imageUrl = "")

        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val totalPrice = shoppingDao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPrice).isEqualTo(2 * 34.5f + 4 * 23.5f + 3 * 14.5f)
    }

}
