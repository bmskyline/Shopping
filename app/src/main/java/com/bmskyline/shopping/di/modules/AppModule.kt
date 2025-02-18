package com.bmskyline.shopping.di.modules

import android.content.Context
import androidx.room.Room
import com.bmskyline.shopping.repository.db.AppDatabase
import com.bmskyline.shopping.repository.db.products.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "products-db")
            .fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideCountriesDao(db: AppDatabase): ProductsDao = db.productsDao()
}
