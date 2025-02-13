package com.bmskyline.shopping.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bmskyline.shopping.repository.db.products.ProductsDao
import com.bmskyline.shopping.repository.model.products.Product


@Database(entities = [Product::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}