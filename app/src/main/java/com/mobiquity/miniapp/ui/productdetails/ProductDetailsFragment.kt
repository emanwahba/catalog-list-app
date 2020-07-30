package com.mobiquity.miniapp.ui.productdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobiquity.miniapp.R
import com.mobiquity.miniapp.databinding.ProductDetailsFragmentBinding

class ProductDetailsFragment : Fragment() {

    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ProductDetailsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.product_details_fragment, container, false)

        if (arguments != null) {
            val arguments = ProductDetailsFragmentArgs.fromBundle(arguments!!)
            val product = arguments.product

            viewModel = ViewModelProvider(this, ProductDetailsViewModelFactory(product))
                .get(ProductDetailsViewModel::class.java)

            binding.productDetailsViewModel = viewModel
        }

        return binding.root
    }
}