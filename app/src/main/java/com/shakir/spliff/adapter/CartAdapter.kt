package com.shakir.spliff.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shakir.spliff.R
import com.shakir.spliff.data.database.ProductDatabase
import com.shakir.spliff.data.model.CartData


class CartAdapter(private val itemClickListener: ItemClickListener,private val context: Context) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
     var cartList = emptyList<CartData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartList[position]

        Glide.with(holder.image)
            .load(currentItem.image)
            .into(holder.image)

        holder.title.text =  currentItem.title
        holder.description.text = currentItem.description
        holder.price.text = currentItem.price.toString()+" $"
        holder.itemNumber.text = currentItem.itemNumber.toString()
        var currentNumber = currentItem.itemNumber
        val basePrice = currentItem.price / currentNumber

        holder.addItem.setOnClickListener {
            if (currentNumber<9){
                currentNumber +=1
            }
            val increasePrice = currentNumber* basePrice
            holder.price.text = "$increasePrice$"
            holder.itemNumber.text = currentNumber.toString()

            val cartItem = CartData(currentItem.id,currentItem.title,increasePrice,currentItem.image,currentItem.description,currentNumber)
            itemClickListener.onItemSend(cartItem)
        }

        holder.minusItem.setOnClickListener {
            if (currentNumber >1){
                currentNumber -=1
            }
            val increasePrice = currentNumber* basePrice
            holder.price.text = "$increasePrice$"
            holder.itemNumber.text = currentNumber.toString()

            val cartItem = CartData(currentItem.id,currentItem.title,increasePrice,currentItem.image,currentItem.description,currentNumber)
            itemClickListener.onItemSend(cartItem)
        }
    }

    override fun getItemCount(): Int {
       return cartList.size
    }

    class CartViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById(R.id.image) as ImageView
        val title = itemView.findViewById(R.id.title) as TextView
        val description = itemView.findViewById(R.id.description) as TextView
        val price = itemView.findViewById(R.id.price) as TextView
        val itemNumber = itemView.findViewById(R.id.number) as TextView
        val addItem = itemView.findViewById(R.id.add_button) as TextView
        val minusItem = itemView.findViewById(R.id.minus_button) as TextView

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(cartList: List<CartData>){
        this.cartList = cartList
        notifyDataSetChanged()
    }

}