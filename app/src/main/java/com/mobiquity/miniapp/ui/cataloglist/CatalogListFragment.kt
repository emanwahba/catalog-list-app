package com.mobiquity.miniapp.ui.cataloglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiquity.miniapp.R
import com.mobiquity.miniapp.model.entities.Catalog
import com.mobiquity.miniapp.model.repository.CatalogRepository
import com.mobiquity.miniapp.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.catalog_list_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class CatalogListFragment : Fragment() {

    @Inject
    lateinit var repository: CatalogRepository
    private lateinit var viewModel: CatalogListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, CatalogListViewModelFactory(repository))
            .get(CatalogListViewModel::class.java)

        viewModel.navigateToProductDetails.observe(
            viewLifecycleOwner, Observer { product ->
                product?.let {
                    this.findNavController().navigate(
                        CatalogListFragmentDirections
                            .actionCatalogListFragmentToProductDetailsFragment(product)
                    )
                    viewModel.onProductDetailsNavigated()
                }
            })

        return inflater.inflate(R.layout.catalog_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(activity)
        catalog_list.layoutManager = linearLayoutManager

        val adapter = CatalogRecyclerViewAdapter(ProductClickListener { product ->
            viewModel.onProductClicked(product)
        })
        catalog_list.adapter = adapter

        viewModel.getCategories().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    progress_bar.visibility = View.GONE
                    catalog_list.visibility = View.VISIBLE
                    if (it.data != null) adapter.filterItemsAndSubmitList(Catalog(it.data))
                }
                Result.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                Result.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                    catalog_list.visibility = View.GONE
                }
            }
        })
    }
}