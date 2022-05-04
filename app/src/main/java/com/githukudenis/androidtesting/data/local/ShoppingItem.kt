package com.githukudenis.androidtesting.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_item_table")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String
)
