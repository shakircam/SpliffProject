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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.models.SlideModel
import com.shakir.spliff.data.database.ProductDao
import com.shakir.spliff.data.database.ProductDatabase
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.CartPrice
import com.shakir.spliff.data.model.CartTitle
import com.shakir.spliff.data.model.ProductData
import com.shakir.spliff.data.viewmodel.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var myViewModel: ProductViewModel
    var number = 1
    var counter = 1
    val cartItems = mutableListOf<CartPrice>()
    var flag = 1


    @SuppressLint("SetTextI18n")
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

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

            if (counter>1){
                counter--
            }
            number = counter / 1
            totalPrice = number* price
            binding.number.text = number.toString()
            binding.totalPrice.text = "$totalPrice$"
        }

        myViewModel.getCartPrice.observe(this,{
            cartItems.addAll(it)
        })


        binding.cart.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO){

                val list = myViewModel.getAllCartTitle()
                val cartTitle = mutableListOf<CartTitle>()
                cartTitle.addAll(list)


                for (i in cartTitle){
                    if (title == i.title) {
                       // if page title & dbTitle is not match this loop will run until list index
                        flag =10
                        break
                    }
                }

                if(flag == 10){
                    // update to cart
                    myViewModel.updateQuantity(title!!,number,totalPrice)
                   // Toast.makeText(this,"Successfully updated cart item",Toast.LENGTH_SHORT).show()
                    Log.d("tag", "update::, $title")

                }else{
                    //inserted to cart
                    val cartData = CartData(
                        0, title!!, totalPrice, image!!, description!!, number
                    )
                    myViewModel.insertCartData(cartData)
                   // Toast.makeText(this,"Successfully updated cart item",Toast.LENGTH_SHORT).show()
                    Log.d("tag", "insert:: $title")

                }
            }

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
        Log.d("title"," cartListSize ::: ${cartItems.size}")
        val intent = Intent(this,CartActivity::class.java)
        startActivity(intent)
    }
}