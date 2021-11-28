package com.shakir.spliff.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shakir.spliff.R
import com.shakir.spliff.databinding.ActivityDetailsBinding
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.models.SlideModel
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.viewmodel.ProductViewModel



class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var myViewModel: ProductViewModel
    var number = 1
    var counter = 1


    @SuppressLint("SetTextI18n")
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        val intent = intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val price = intent.getIntExtra("price",0)
        Log.d("price",price.toString())

        var totalPrice = price
         val image = intent.getStringExtra("image")
         Log.d("url",image.toString())
         val imageList = ArrayList<SlideModel>()
         imageList.add(SlideModel(image))
         imageList.add(SlideModel(image))
         imageList.add(SlideModel(image))
         binding.imageSlider.setImageList(imageList)

        binding.title.text = title
        binding.description.text = description
        binding.price.text = "$price$"
        binding.totalPrice.text = "$price$"

        binding.addButton.setOnClickListener {
            counter ++
            number = counter * 1
            totalPrice = number* price
            binding.number.text = number.toString()
            binding.totalPrice.text = "$totalPrice$"
        }

        binding.minusButton.setOnClickListener {
            counter--
            number = counter / 1
            totalPrice = number* price
            binding.number.text = number.toString()
            binding.totalPrice.text = "$totalPrice$"
        }

        binding.cart.setOnClickListener {
            Log.d("data",title!!)

                val cartData = CartData(
                    title, totalPrice,image!!,description!!,number)
                myViewModel.insertCartData(cartData)

            val intent= Intent(this,CartActivity::class.java)
                startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.cart_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add -> openCart()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openCart() {
        val intent = Intent(this,CartActivity::class.java)
        startActivity(intent)
    }
}