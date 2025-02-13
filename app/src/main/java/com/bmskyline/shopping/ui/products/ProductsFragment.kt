package com.bmskyline.shopping.ui.products

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.bmskyline.shopping.R
import com.bmskyline.shopping.base.BaseFragment
import com.bmskyline.shopping.databinding.FragmentProductsBinding
import com.bmskyline.shopping.repository.model.products.Product
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {

    private val productsViewModel: ProductsViewModel by activityViewModels()

    private lateinit var productsAdapter: ProductsAdapter
    private var listOfProducts = ArrayList<Product>()
    private var isGridLayout = false

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProductsBinding = FragmentProductsBinding.inflate(inflater, container, false)

    companion object {

        @JvmStatic
        fun newInstance() = ProductsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_toggle_layout -> {
                        isGridLayout = !isGridLayout
                        binding?.recyclerviewProducts?.let { it1 -> switchLayout(it1, isGridLayout, 2) }
                        true
                    }
                    R.id.action_sort -> {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Sort By")
                            .setItems(arrayOf("Name", "Id")) { dialog, which ->
                                when (which) {
                                    0 -> {
                                        productsAdapter.sortByName()
                                    }
                                    1 -> {
                                        productsAdapter.sortById()
                                    }
                                }
                            }.show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        load()
        observeProducts()
        binding?.fab?.setOnClickListener {
            isGridLayout = !isGridLayout
            binding?.recyclerviewProducts?.let { it1 -> switchLayout(it1, isGridLayout, 2) }
        }
    }

    @OptIn(ExperimentalBadgeUtils::class)
    private fun load() {
        binding?.recyclerviewProducts?.layoutManager = LinearLayoutManager(context)
        productsAdapter = ProductsAdapter(listOfProducts, isGridLayout)
        binding?.recyclerviewProducts?.adapter = productsAdapter

        productsAdapter.onProductClicked = { product ->
            val badgeText = productsAdapter.getSavesProductSize().toString()
            binding?.badgeText?.text = badgeText
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeProducts() {
        productsViewModel.products.observe(viewLifecycleOwner) { products ->
            if (products.isNullOrEmpty()) {
                productsViewModel.refreshProducts()
            } else {
                listOfProducts.clear()
                listOfProducts.addAll(products)
                productsAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun switchLayout(recyclerView: RecyclerView, isGridLayout: Boolean, spanCount: Int = 2) {
        if (isGridLayout) {
            recyclerView.layoutManager = GridLayoutManager(recyclerView.context, spanCount)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        }
        productsAdapter.setLayoutMode(isGridLayout)
        productsAdapter.notifyDataSetChanged()
    }
}
