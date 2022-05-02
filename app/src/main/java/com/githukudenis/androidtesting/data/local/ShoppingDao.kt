package com.githukudenis.androidtesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import dagger.internal.DaggerGenerated

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_item_table")
    fun observeShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT sum(price * amount) FROM shopping_item_table")
    fun observeTotalPrice(): LiveData<Float>
    
}