package com.keennhoward.bruntwork.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.cart.CartRepository
import com.keennhoward.bruntwork.cart.CartViewModel
import com.keennhoward.bruntwork.cart.CartViewModelFactory
import com.keennhoward.bruntwork.databinding.FragmentCartBinding
import com.keennhoward.bruntwork.databinding.FragmentCheckoutBinding
import com.keennhoward.bruntwork.db.room.CartDatabase
import com.keennhoward.bruntwork.db.room.CartProductModel
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var checkoutViewModel: CheckoutViewModel

    private lateinit var cartProducts: ArrayList<CartProductModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        val view = binding.root

        val dao = CartDatabase.getInstance(requireActivity().application).cartDao()
        val repository = CheckoutRepository(dao)
        val factory = CheckoutViewModelFactory(repository)
        checkoutViewModel =
            ViewModelProvider(requireActivity(), factory).get(CheckoutViewModel::class.java)

        checkoutViewModel.cart.observe(requireActivity(), Observer {
            cartProducts = ArrayList(it)
        })

        binding.checkoutButton.setOnClickListener {
            if (binding.checkoutName.text.toString().trim().isEmpty()){
                Log.d("Checker", "no name")
            }else if(binding.checkoutEmail.text.toString().trim().isEmpty()){
                Log.d("Checker", "no email")
            } else if(!binding.checkoutTermsSwitch.isChecked){
                Log.d("Checker", "switch un checked")
            }else{

                //checks if cart data is initialized
                if (this::cartProducts.isInitialized) {

                    //creates a new filename if it exits already
                    var uniqueID = UUID.randomUUID().toString()
                    while (File(requireActivity().filesDir, "order_${uniqueID}.json").exists()) {
                        uniqueID = UUID.randomUUID().toString()
                    }

                    //create json string
                    val gson = Gson()
                    val json = gson.toJson(
                        createOrder(
                            cartProducts,
                            uniqueID,
                            binding.checkoutName.text.toString(),
                            binding.checkoutEmail.text.toString(),
                            cartTotalPrice(cartProducts).toString()
                        )
                    )

                    //creates json file of order
                    File(requireActivity().filesDir, "order_${uniqueID}.json").printWriter()
                        .use { out ->
                            out.println(json)
                        }
                    checkoutViewModel.clearCart()
                } else {
                    Log.d("Checker", "cart not initialized")
                }
            }
        }
        return view
    }

    private fun createOrder(
        cart: ArrayList<CartProductModel>,
        id: String,
        name: String,
        email: String,
        totalPrice:String
    ): OrderModel {
        return OrderModel(
            id = id,
            name = name,
            email = email,
            products = cart,
            total_price = totalPrice
        )
    }


    private fun cartTotalPrice(cart:ArrayList<CartProductModel>):Double{
        var totalPrice = 0.00
        for(cartItem in cart){
            totalPrice += cartItem.price.toDouble()
        }
        return totalPrice
    }

}