package com.keennhoward.bruntwork.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.keennhoward.bruntwork.model.Utils.getJsonDataFromAsset
import com.keennhoward.bruntwork.databinding.FragmentProductsBinding
import com.keennhoward.bruntwork.model.Product
import com.keennhoward.bruntwork.room.CartDatabase

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null

    //private val viewModel by viewModels<ProductsViewModel>()
    private lateinit var productsViewModel: ProductsViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val view = binding.root

        val dao = CartDatabase.getInstance(requireActivity().application).cartDao()
        val repository = ProductsRepository(dao)
        val factory = ProductsViewModelFactory(repository,requireActivity().application)

        productsViewModel = ViewModelProvider(requireActivity(), factory).get(ProductsViewModel::class.java)

        binding.productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = ProductsAdapter(productsViewModel.products, requireActivity().application)
        }


        /*
        val jsonFileString = getJsonDataFromAsset(view.context, "products.json")
        Log.i("data", jsonFileString)

        val gson = Gson()

        val objectList = gson.fromJson(jsonFileString, Product::class.java)
        Log.i("data", objectList.products.size.toString())

         */
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}