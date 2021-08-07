package com.zaki.rts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.zaki.recylerviewtabscroller.LinearLayoutManagerWithSmoothScroller
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var categoryTabs = ArrayList<Tab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTabs()

    }

    private fun setTabs() {

        //Add Tabs
        getSampleData().forEachIndexed { index, catalog ->
            if (catalog.isCategory) {
                tlCategory.addTab(tlCategory.newTab().setText(catalog.name))
                categoryTabs.add(Tab(catalog.categoryId, catalog.name))
                tlCategory.tabGravity = TabLayout.GRAVITY_FILL
            }
        }
        //Attach to Recyler View
        rvCatalog.setTabLayout(tlCategory)
        //Set Smooth Scroll
        rvCatalog.setSmoothScroll(true)

        setAdapter()
    }

    private fun setAdapter() {
        //Set Linked Tab with items in Recycler
        rvCatalog.apply {
            setCountItemsByTabIndex(getCountOfTabItems())
            addOnScrollListener(RecyclerViewScrollListener())
            layoutManager = LinearLayoutManagerWithSmoothScroller(
                this@MainActivity,
                RecyclerView.VERTICAL,
                false
            )
            adapter = CatalogAdapter(getSampleData())
        }
    }

    private fun getCountOfTabItems(): ArrayList<Int> {
        var indexes = ArrayList<Int>()
        categoryTabs.forEach {
            var count = 0
            getSampleData().forEach { ct ->
                if (it.categoryId == ct.categoryId) {
                    count++
                }
            }
            indexes.add(count)
        }
        return indexes
    }

    private fun getSampleData(): ArrayList<Catalog> {
        var catalogList = ArrayList<Catalog>()
        catalogList.add(Catalog(categoryId = "1", name = "Category 1", isCategory = true))
        catalogList.add(Catalog(productId = "1", name = "Product 1",categoryId = "1"))
        catalogList.add(Catalog(productId = "2", name = "Product 2",categoryId = "1"))
        catalogList.add(Catalog(productId = "3", name = "Product 3",categoryId = "1"))
        catalogList.add(Catalog(productId = "4", name = "Product 4",categoryId = "1"))
        catalogList.add(Catalog(productId = "5", name = "Product 5",categoryId = "1"))
        catalogList.add(Catalog(productId = "6", name = "Product 6",categoryId = "1"))
        catalogList.add(Catalog(categoryId = "2", name = "Category 2", isCategory = true))
        catalogList.add(Catalog(productId = "7", name = "Product 7",categoryId = "2"))
        catalogList.add(Catalog(productId = "8", name = "Product 8",categoryId = "2"))
        catalogList.add(Catalog(productId = "9", name = "Product 9",categoryId = "2"))
        catalogList.add(Catalog(productId = "10", name = "Product 10",categoryId = "2"))
        catalogList.add(Catalog(categoryId = "3", name = "Category 3", isCategory = true))
        catalogList.add(Catalog(productId = "11", name = "Product 11",categoryId = "3"))
        catalogList.add(Catalog(categoryId = "4", name = "Category 4", isCategory = true))
        catalogList.add(Catalog(productId = "12", name = "Product 12",categoryId = "4"))
        catalogList.add(Catalog(productId = "13", name = "Product 13",categoryId = "4"))
        catalogList.add(Catalog(productId = "14", name = "Product 14",categoryId = "4"))
        catalogList.add(Catalog(productId = "15", name = "Product 15",categoryId = "4"))
        catalogList.add(Catalog(productId = "16", name = "Product 16",categoryId = "4"))
        catalogList.add(Catalog(productId = "17", name = "Product 17",categoryId = "4"))
        catalogList.add(Catalog(categoryId = "5", name = "Category 5", isCategory = true))
        catalogList.add(Catalog(productId = "18", name = "Product 18",categoryId = "5"))
        catalogList.add(Catalog(productId = "19", name = "Product 19",categoryId = "5"))
        catalogList.add(Catalog(productId = "20", name = "Product 20",categoryId = "5"))
        return catalogList
    }
}