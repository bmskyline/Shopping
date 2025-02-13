package com.bmskyline.shopping.repository.db.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bmskyline.shopping.repository.model.products.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(countries: List<Product>)

    @Query("SELECT * FROM products_table")
    fun getProducts(): Flow<List<Product>>

    @Query("DELETE FROM products_table")
    suspend fun deleteAllProducts()
}