package com.shakir.spliff.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.shakir.spliff.R
import com.shakir.spliff.data.database.ProductDatabase
import com.shakir.spliff.data.model.CartData
import com.shakir.spliff.data.model.CartTitle
import com.shakir.spliff.data.viewmodel.ProductViewModel
import com.shakir.spliff.databinding.FragmentAddToBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddToFragment : DialogFragment() {


    private var _binding : FragmentAddToBinding? = null
    private val binding get() = _binding!!
    var number = 1
    var counter = 1
    var flag =0
    var newnumber=0
    var newPrice=0
    private lateinit var myViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddToBinding.inflate(inflater, container, false)
        myViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        binding.addButton.setOnClickListener {
            if (counter<9){
                counter ++
            }
            number = counter * 1
            binding.number.text = number.toString()
        }

        binding.minusButton.setOnClickListener {
            if (counter>1){
                counter--
            }
            number = counter / 1
            binding.number.text = number.toString()

        }

        binding.cart.setOnClickListener {
            setFragmentResultListener("key"){requestKey, bundle ->
                val title = bundle.getString("title")
                val description = bundle.getString("description")
                val id = bundle.getInt("id")
                val image = bundle.getString("image")
                val price = bundle.getInt("price")
                var totalPrice = price
                Log.d("key",":::$title")


                lifecycleScope.launch(Dispatchers.IO){
                    val productDao = ProductDatabase.getDatabase(requireContext()).productDao()
                    val list = productDao.allCartTitle()
                    val allList = productDao.allCartList()
                    val cartList = mutableListOf<CartData>()
                    cartList.addAll(allList)

                    val cartTitle = mutableListOf<CartTitle>()
                    cartTitle.addAll(list)

                    for (i in cartList){
                        if (title == i.title) {
                            newnumber = i.itemNumber+number
                            newPrice = (i.price/i.itemNumber)*newnumber
                            flag =10
                            break
                        }
                    }

                    if(flag == 10){
                        // update
                        Log.d("tag", number.toString())

                        myViewModel.updateQuantity(title!!,newnumber,newPrice)
                        Log.d("tag", "update::, $title")

                    }else{

                        val cartData = CartData(
                            0, title!!, totalPrice, image!!, description!!, number
                        )
                        myViewModel.insertCartData(cartData)
                        Log.d("tag", "insert:: $title")

                    }
                }



            }

            dismiss()
        }

        return binding.root
    }

}