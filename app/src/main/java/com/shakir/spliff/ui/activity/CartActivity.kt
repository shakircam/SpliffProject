package com.shakir.spliff.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.shakir.spliff.adapter.CartAdapter
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.CartPrice
import com.shakir.spliff.data.viewmodel.ProductViewModel
import com.shakir.spliff.databinding.ActivityCartBinding
import com.shakir.spliff.utils.SwipeToDelete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartActivity : AppCompatActivity() {
    private val adapter by lazy { CartAdapter() }
    private lateinit var myViewModel: ProductViewModel
    private lateinit var binding: ActivityCartBinding
    var totalAmount = 0
    val cartItems = mutableListOf<CartPrice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        binding.account.setOnClickListener {
            val intent =  Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        myViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        initRecyclerView()

        myViewModel.getAllCartData.observe(this,{
            adapter.setData(it)
        })
        adapter.grandTotal()

        myViewModel.getCartPrice.observe(this,{

            cartItems.addAll(it)
            for (i in cartItems.indices){
                totalAmount += cartItems[i].price
            }

            binding.price.text ="$totalAmount"
        })
        //getPrice()
    }


    private fun getPrice(){
        lifecycleScope.launch(Dispatchers.IO){
            val price =  myViewModel.getAllCartPrice()
            val priceList = mutableListOf<CartPrice>()
            priceList.addAll(price)

            for (i in priceList.indices){
                totalAmount += priceList[i].price
            }

            binding.price.text ="$totalAmount"
        }
    }

    private fun initRecyclerView() {

        val mRecyclerView = binding.recyclerview
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        swipeToDelete(mRecyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.cartList[viewHolder.adapterPosition]
                // Delete Item
                myViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                // Restore Deleted Item
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    private fun restoreDeletedData(view: View, deletedItem: CartData) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            myViewModel.insertCartData(deletedItem)
        }
        snackBar.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}