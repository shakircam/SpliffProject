package com.shakir.spliff.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.CartPrice
import com.shakir.spliff.data.model.CartTitle
import com.shakir.spliff.data.model.ProductData

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ProductData>>

    @Query("SELECT * FROM cart_table ORDER BY id ASC")
    fun getAllCartData(): LiveData<List<CartData>>

    @Query("SELECT title FROM cart_table")
    suspend fun getAllCartTitle(): List<CartTitle>

    @Query("SELECT * FROM cart_table ")
    suspend fun getAllCartPrice(): List<CartPrice>

    @Query("SELECT * FROM cart_table ")
     fun getCartPrice(): LiveData<List<CartPrice>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ArrayList<ProductData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartData(cartData: CartData)

    @Delete
    suspend fun deleteItem(cartData: CartData)


    @Query("SELECT * FROM cart_table WHERE id = :id")
     fun getItemById(id : Int) : List<CartData>

    @Query("UPDATE cart_table SET itemNumber = itemNumber+:itemNumber,price = price+:price   WHERE title = :title")
    fun updateQuantity(title : String, itemNumber : Int, price : Int)

    @Query("SELECT title FROM cart_table WHERE title = :title")
    suspend fun getItemId(title: String): CartTitle

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCartData(cartData: CartData)

    @Query("SELECT * FROM product_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<ProductData>>
}