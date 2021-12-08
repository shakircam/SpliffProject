package com.shakir.spliff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shakir.spliff.R
import com.shakir.spliff.data.model.ProductData
import com.shakir.spliff.ui.fragment.BoarderFragmentDirections
import com.shakir.spliff.ui.fragment.FlowersFragmentDirections

class ProductAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder> () {

    private var productList = emptyList<ProductData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.spliff_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]

        Glide.with(holder.image)
            .load(currentItem.image)
            .into(holder.image)

        holder.title.text =  currentItem.title
        holder.description.text = currentItem.description
        holder.price.text = currentItem.price.toString()+" $"
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
        holder.addButton.setOnClickListener {
          itemClickListener.onAddClick()

        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById(R.id.image) as ImageView
        val title = itemView.findViewById(R.id.title) as TextView
        val description = itemView.findViewById(R.id.description) as TextView
        val price = itemView.findViewById(R.id.price) as TextView
        val addButton = itemView.findViewById(R.id.add) as Button
    }

    fun setData(productList: List<ProductData>){
        this.productList = productList
        notifyDataSetChanged()
    }

}