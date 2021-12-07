package com.shakir.spliff.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.ProductData

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ProductData>>

    @Query("SELECT * FROM cart_table ORDER BY id ASC")
    fun getAllCartData(): LiveData<List<CartData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ArrayList<ProductData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartData(cartData: CartData)

    @Delete
    suspend fun deleteItem(cartData: CartData)


    @Query("UPDATE cart_table SET itemNumber = itemNumber + 1  WHERE title = :title")
    fun updateQuantity(title : String)

    @Query("SELECT title FROM cart_table WHERE title = :title LIMIT 1")
    fun getItemId(title: String): String?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCartData(cartData: CartData)

    @Query("SELECT * FROM product_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<ProductData>>
}