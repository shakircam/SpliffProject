package com.shakir.spliff.adapter

import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.ProductData

interface ItemClickListener {
    fun onItemClick(position : Int)
    fun onItemSend(cartData: CartData)
    fun onAddClick(productData: ProductData)
}