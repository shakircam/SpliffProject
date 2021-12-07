package com.shakir.spliff.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var price: Int,
    var image: String,
    var description: String,
    var itemNumber: Int
)




