package com.bmskyline.shopping.repository.repo.products

import android.content.Context
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.bmskyline.shopping.repository.db.products.ProductsDao
import com.bmskyline.shopping.repository.model.products.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val productsDao: ProductsDao,
    @ApplicationContext val context: Context
) {

    val getProducts: Flow<List<Product>> = productsDao.getProducts()

    @WorkerThread
    suspend fun insertProducts() {
        val listOfProduct: List<Product> = getProductListFromJson(context, "products.json") ?: emptyList()
        productsDao.deleteAllProducts()
        productsDao.insertProducts(listOfProduct)
    }

    private fun getProductListFromJson(context: Context, fileName: String): List<Product>? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)

            val gson = Gson()
            val listType = object : TypeToken<List<Product>>() {}.type
            gson.fromJson(jsonString, listType)

        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}