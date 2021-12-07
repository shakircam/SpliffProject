package com.shakir.spliff.data.repository

import androidx.lifecycle.LiveData
import com.shakir.spliff.data.database.ProductDao
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.CartTitle
import com.shakir.spliff.data.model.ProductData

class ProductRepository(private val productDao: ProductDao) {

    val getAllData: LiveData<List<ProductData>> = productDao.getAllData()
    val getAllCartData: LiveData<List<CartData>> = productDao.getAllCartData()


    fun getItemId(title: String): String{
      return productDao.getItemId(title).toString()

    }

    suspend fun insertData(productData: ArrayList<ProductData>){
        productDao.insertData(productData)
    }
    suspend fun insertCartData(cartData: CartData){
        productDao.insertCartData(cartData)
    }
    suspend fun updateCartData(cartData: CartData){
        productDao.updateCartData(cartData)
    }

    suspend fun updateQuantity(title : String, itemNumber : Int){
        productDao.updateQuantity(title, itemNumber)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ProductData>> {
        return productDao.searchDatabase(searchQuery)
    }

    suspend fun deleteItem(cartData: CartData){
        productDao.deleteItem(cartData)
    }


}