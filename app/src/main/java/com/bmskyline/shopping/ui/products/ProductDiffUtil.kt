package com.bmskyline.shopping.ui.products

import androidx.recyclerview.widget.DiffUtil
import com.bmskyline.shopping.repository.model.products.Product

class ProductDiffUtil(private val oldList:List<Product>, private val newList:List<Product>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return  newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]

        return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.imageUrl == newItem.imageUrl

    }

}