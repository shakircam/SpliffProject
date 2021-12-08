package com.shakir.spliff.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.shakir.spliff.R
import com.shakir.spliff.adapter.ItemClickListener
import com.shakir.spliff.adapter.PagerAdapter
import com.shakir.spliff.adapter.ProductAdapter
import com.shakir.spliff.data.viewmodel.ProductViewModel
import com.shakir.spliff.databinding.ActivityHomeBinding
import com.shakir.spliff.databinding.ActivityListBinding
import com.shakir.spliff.ui.fragment.*

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(FlowersFragment())
        fragments.add(VapesFragment())
        fragments.add(ExtractsFragment())
        fragments.add(EdiblesFragment())
        fragments.add(AccessoriesFragment())


        val title = ArrayList<String>()
        title.add("Flowers")
        title.add("Vapes")
        title.add("Extracts")
        title.add("Edibles")
        title.add("Accessories")

        val pagerAdapter = PagerAdapter(
            fragments,
            this
        )
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = title[position]
        }.attach()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}