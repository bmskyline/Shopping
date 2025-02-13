package com.bmskyline.shopping.ui.products

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bmskyline.shopping.databinding.GridProductItemBinding
import com.bmskyline.shopping.databinding.LinearProductItemBinding
import com.bmskyline.shopping.repository.model.products.Product
import com.bmskyline.shopping.utils.load

class ProductsAdapter(private val products: MutableList<Product>, private var isGridLayout: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onProductClicked: ((Product) -> Unit)? = null
    var savedProduct = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view = GridProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolder(view)
            }

            else -> {
                val view = LinearProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LinearViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val country = products[position]
        when (holder) {
            is GridViewHolder -> holder.bindView(country, position)
            is LinearViewHolder -> holder.bindView(country, position)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridLayout) 0 else 1
    }
    override fun getItemCount(): Int = products.size

    fun setLayoutMode(isGrid: Boolean) {
        this.isGridLayout = isGrid
    }

    fun addSavedProduct(number: Int) {
        if (savedProduct.contains(number))
            savedProduct.remove(number)
        else savedProduct.add(number)

    }

    fun getSavesProductSize(): Int {
        return savedProduct.size
    }

    fun sortByName() {
        val sortedList = products.sortedBy { it.name }
        val diffCallback = ProductDiffUtil(products, sortedList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        products.clear()
        products.addAll(sortedList)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    fun sortById() {
        val sortedList = products.sortedBy { it.id }
        val diffCallback = ProductDiffUtil(products, sortedList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        products.clear()
        products.addAll(sortedList)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    inner class LinearViewHolder(private val itemBinding: LinearProductItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindView(product: Product, position: Int) {
            itemBinding.apply {
                root.setOnClickListener {
                    addSavedProduct(product.id)
                    notifyItemChanged(position)
                    onProductClicked?.invoke(products[position])
                }
                tvCountryName.text = product.name
                tvPrice.text = product.price
                icCheck.visibility = if (savedProduct.contains(product.id)) View.VISIBLE else View.INVISIBLE
                ivCountryImage.load(
                    itemBinding.root.context,
                    Uri.parse(product.imageUrl).toString()
                )
            }
        }
    }

    inner class GridViewHolder(private val itemBinding: GridProductItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bindView(product: Product, position: Int) {
            itemBinding.apply {
                root.setOnClickListener {
                    addSavedProduct(product.id)
                    notifyItemChanged(position)
                    onProductClicked?.invoke(products[position])
                }
                tvName.text = product.name
                tvPrice.text = product.price
                icCheck.visibility = if (savedProduct.contains(product.id)) View.VISIBLE else View.INVISIBLE
                ivImage.load(
                    itemBinding.root.context,
                    Uri.parse(product.imageUrl).toString()
                )
            }
        }
    }
}
