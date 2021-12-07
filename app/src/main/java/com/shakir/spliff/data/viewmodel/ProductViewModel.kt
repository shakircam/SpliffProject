package com.shakir.spliff.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.shakir.spliff.data.database.ProductDatabase
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.CartTitle
import com.shakir.spliff.data.model.ProductData
import com.shakir.spliff.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val productDao = ProductDatabase.getDatabase(
        application
    ).productDao()
    private val repository: ProductRepository = ProductRepository(productDao)

    val getAllData: LiveData<List<ProductData>> = repository.getAllData
    val getAllCartData: LiveData<List<CartData>> = repository.getAllCartData


    fun insertData(productData: ArrayList<ProductData>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(productData)
        }
    }

    fun insertCartData(cartData: CartData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCartData(cartData)
        }
    }

    fun updateCartData(cartData: CartData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCartData(cartData)
        }
    }
    fun getItemId(title: String): String {

          return  repository.getItemId(title)
    }


    fun searchDatabase(searchQuery: String): LiveData<List<ProductData>>{
        return repository.searchDatabase(searchQuery)
    }

    fun deleteItem(cartData: CartData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(cartData)
        }
    }

    fun updateQuantity(title : String, itemNumber : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateQuantity(title,itemNumber)
        }
    }

}