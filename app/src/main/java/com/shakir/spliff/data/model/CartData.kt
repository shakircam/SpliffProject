package com.shakir.spliff.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartData(
    var title: String,
    var price: Int,
    var image: String,
    var description: String,
    var itemNumber: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
