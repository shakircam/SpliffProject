package com.shakir.spliff.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "product_table")
@Parcelize
data class ProductData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var price: Int,
    var image: String,
    var description: String
): Parcelable
