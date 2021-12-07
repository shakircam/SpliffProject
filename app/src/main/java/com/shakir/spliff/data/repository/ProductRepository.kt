package com.shakir.spliff.data.repository

import android.util.Log.e
import androidx.lifecycle.LiveData
import androidx.startup.StartupLogger.e
import com.shakir.spliff.data.database.ProductDao
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.CartTitle
import com.shakir.spliff.data.model.ProductData
import com.shakir.spliff.utils.DataFetchCallback
import java.util.logging.Logger

class ProductRepository(private val productDao: ProductDao) {

    val getAllData: LiveData<List<ProductData>> = productDao.getAllData()
    val getAllCartData: LiveData<List<CartData>> = productDao.getAllCartData()
    val getAllCartTitle: LiveData<List<CartTitle>> = productDao.getTitle()

   suspend fun getItemId(title: String, callback:DataFetchCallback<CartTitle>){
       try {
           callback.onSuccess(productDao.getItemId(title))
       } catch (e: Exception){
           callback.onError(e)
          // Logger.e(e.localizedMessage)
       }
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

    fun searchDatabase(searchQuery: String): LiveData<List<ProductData>> {
        return productDao.searchDatabase(searchQuery)
    }

    suspend fun deleteItem(cartData: CartData){
        productDao.deleteItem(cartData)
    }
}