package com.mobiquity.miniapp.ui.cataloglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobiquity.miniapp.databinding.ListItemHeaderBinding
import com.mobiquity.miniapp.databinding.ListItemProductBinding
import com.mobiquity.miniapp.model.entities.Catalog
import com.mobiquity.miniapp.model.entities.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_CATEGORY = 0
private const val ITEM_VIEW_TYPE_PRODUCT = 1

class CatalogRecyclerViewAdapter(private val clickListener: ProductClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(CatalogDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun filterItemsAndSubmitList(catalog: Catalog) {
        adapterScope.launch {
            var items: List<DataItem> = listOf()
            for (category in catalog.categories) {
                items = items +
                        listOf(DataItem.CategoryHeaderItem(category.id, category.name)) +
                        category.products.map { DataItem.ProductItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_CATEGORY -> CategoryHeaderViewHolder.from(
                parent
            )
            ITEM_VIEW_TYPE_PRODUCT -> ProductViewHolder.from(
                parent
            )
            else -> throw ClassCastException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryHeaderViewHolder -> {
                val item = getItem(position) as DataItem.CategoryHeaderItem
                holder.bind(item.categoryName)
            }
            is ProductViewHolder -> {
                val item = getItem(position) as DataItem.ProductItem
                holder.bind(item.product, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.CategoryHeaderItem -> ITEM_VIEW_TYPE_CATEGORY
            is DataItem.ProductItem -> ITEM_VIEW_TYPE_PRODUCT
        }
    }

    class CategoryHeaderViewHolder(private val binding: ListItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CategoryHeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHeaderBinding.inflate(layoutInflater, parent, false)
                return CategoryHeaderViewHolder(binding)
            }
        }

        fun bind(categoryName: String) {
            binding.name = categoryName
        }
    }

    class ProductViewHolder(private val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemProductBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding)
            }
        }

        fun bind(item: Product, clickListener: ProductClickListener) {
            binding.product = item
            binding.clickListener = clickListener
        }
    }
}

class CatalogDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    abstract val id: String

    data class ProductItem(val product: Product) : DataItem() {
        override val id = product.categoryId + "_" + product.id
    }

    data class CategoryHeaderItem(val categoryId: String, val categoryName: String) : DataItem() {
        override val id = categoryId
    }
}

class ProductClickListener(val clickListener: (product: Product) -> Unit) {
    fun onClick(product: Product) = clickListener(product)
}