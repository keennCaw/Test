package com.keennhoward.bruntwork.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.keennhoward.bruntwork.model.Utils.getJsonDataFromAsset
import com.keennhoward.bruntwork.databinding.FragmentProductsBinding
import com.keennhoward.bruntwork.model.Product

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val view = binding.root

        val jsonFileString = getJsonDataFromAsset(view.context, "products.json")
        Log.i("data", jsonFileString)

        val gson = Gson()

        val objectList = gson.fromJson(jsonFileString, Product::class.java)
        Log.i("data", objectList.products.size.toString())

        /*
        val listProductType = object : TypeToken<List<Product>>() {}.type

        var products: List<Product> = gson.fromJson(jsonFileString, listProductType)
        products.forEachIndexed { idx, product -> Log.i("data", "> Item $idx:\n$product") }
         */

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}