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
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.models.SlideModel
import com.shakir.spliff.data.database.ProductDao
import com.shakir.spliff.data.database.ProductDatabase
import com.shakir.spliff.data.model.CartData
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
    //private lateinit var productDao : ProductDao
    val cartItems = mutableListOf<CartData>()
    val list = mutableListOf<CartTitle>()

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

       //productDao.getItemByTitle(title!!)
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

        myViewModel.getAllCartData.observe(this,{

            cartItems.addAll(it)
        })
        Log.d("title"," cartListSize in main page ::: ${cartItems.size}")

        lifecycleScope.launch(Dispatchers.IO){
            getData()
        }



        binding.cart.setOnClickListener {

                for (i in list.indices){
                    Log.d("list" ,"cardAllData ${list[i].title}")
                    Log.d("titlepage","title1 -> $title ::: title2 -> ${list[i].title}, $i ::: ${list.size}")
                    if(title == list[i].title){
                        Log.d("update",list[i].title)
                        // update
                        val cartData = CartData(
                            0,title, totalPrice, image!!, description!!, number
                        )
                        myViewModel.updateCartData(cartData)
                        break

                    }else{
                        Log.d("insert",list[i].title)
                        //insert
                        val cartData = CartData(
                            0,title!!, totalPrice, image!!, description!!, number
                        )
                        myViewModel.insertCartData(cartData)
                        break
                    }
                }


           // Log.d("title"," cartListSize after click ::: ${item.size}")


         /*  for(i in  cartItems.indices){
               Log.d("titlelist", "${cartItems.size},${cartItems[i].title}")

             Log.d("titlepage","title1 -> $title ::: title2 -> ${cartItems[i].title}, $i ::: ${cartItems.size}")
                if(title == cartItems[i].title){
                    Log.d("update",cartItems[i].title)
                    // update
                    val cartData = CartData(
                        0,title!!, totalPrice, image!!, description!!, number
                    )
                    myViewModel.updateCartData(cartData)
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }else{
                    Log.d("insert",cartItems[i].title)
                    //insert
                    val cartData = CartData(
                        0,title!!, totalPrice, image!!, description!!, number
                    )
                    myViewModel.insertCartData(cartData)
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    break
                }

            }*/

        /*    val array = arrayOf("a","b","c","d")
            val a = "d"
            for ( i in array.indices){
                Log.d("text",array[i])
                if (a == array[i]){
                    Log.d("text","match")
                    break
                }else{
                    Log.d("text","not match")
                }
            }*/

        }

    }

    suspend fun getData(){
        val db = ProductDatabase.getDatabase(applicationContext)
        val data = db.productDao().allCartData()
        //val list = mutableListOf<CartTitle>()
        list.addAll(data)
        for (i in list.indices){
            Log.d("list" ,"cardAllData ${list[i].title}")
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