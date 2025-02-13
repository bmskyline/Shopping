package com.bmskyline.shopping.ui.products

import android.os.Bundle
import com.bmskyline.shopping.R
import com.bmskyline.shopping.base.BaseActivity
import com.bmskyline.shopping.databinding.ActivityProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : BaseActivity<ActivityProductsBinding>() {

    override fun createBinding(): ActivityProductsBinding =
        ActivityProductsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, ProductsFragment.newInstance())
                .commit()
        }
    }
}
