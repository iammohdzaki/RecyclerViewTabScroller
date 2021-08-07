package com.zaki.rts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view_category.view.*
import kotlinx.android.synthetic.main.item_view_product.view.*

/**
Created by Mohammad Zaki
on Aug,07 2021
 **/
class CatalogAdapter(var catalogList: ArrayList<Catalog>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var CATEGORY = 1
    var PRODUCT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CATEGORY) {
            val categoryView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_category, parent, false)
            return CategoryHolder(categoryView)
        } else {
            val productView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_product, parent, false)
            return ProductHolder(productView)
        }
    }

    override fun getItemCount(): Int {
        return catalogList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (catalogList[position].isCategory) {
            CATEGORY
        } else {
            PRODUCT
        }
    }

    inner class CategoryHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.apply {
                tvCategoryName.text = catalogList[position].name
            }
        }
    }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.apply {
                tvId.text = catalogList[position].productId
                tvName.text = catalogList[position].name
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryHolder) {
            holder.bind(position)
        } else {
            (holder as ProductHolder).bind(position)
        }
    }
}