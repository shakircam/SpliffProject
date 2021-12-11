package com.shakir.spliff.ui.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.shakir.spliff.adapter.CartAdapter
import com.shakir.spliff.adapter.ItemClickListener
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.CartPrice
import com.shakir.spliff.data.model.ProductData
import com.shakir.spliff.data.viewmodel.ProductViewModel
import com.shakir.spliff.databinding.ActivityCartBinding
import com.shakir.spliff.utils.SwipeToDelete


class CartActivity : AppCompatActivity(), ItemClickListener {

    private val adapter by lazy { CartAdapter(this,this) }

    private lateinit var myViewModel: ProductViewModel
    private lateinit var binding: ActivityCartBinding

    val cartItems = mutableListOf<CartData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.account.setOnClickListener {
            val intent =  Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        myViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        initRecyclerView()

        myViewModel.getAllCartData.observe(this,{
            adapter.setData(it)
        })

        myViewModel.getCartPrice.observe(this,{

            Log.d("tag", it.toString())
            val totalPrice= it.sumOf { it.price }
            binding.price.text ="$totalPrice"
        })


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
        saveToDb()
        return true
    }

    private fun saveToDb() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setPositiveButton("Save") { _, _ ->

            onBackPressed()
            Toast.makeText(this," Successfully save to cartDb",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Not Interested") { _, _ ->
            Toast.makeText(this,"Not save to cartDb",Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("Save Database?")
        builder.setMessage("Are you want to Save?")
        val alertDialog : android.app.AlertDialog = builder.create()
        alertDialog.show()
    }

    override fun onItemSend(cartData: CartData) {
        cartItems.add(cartData)
        myViewModel.updateCartData(cartData)
    }
    override fun onItemClick(position: Int) {
       // nothing...
    }
    override fun onAddClick(productData: ProductData) {
        // nothing......
    }

}