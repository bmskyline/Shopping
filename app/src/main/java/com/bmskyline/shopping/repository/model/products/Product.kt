package com.bmskyline.shopping.repository.model.products

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class Product(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "price") var price: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "imageUrl") var imageUrl: String? = null
)