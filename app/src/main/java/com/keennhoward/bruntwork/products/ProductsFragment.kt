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

        val productsAdapter = ProductsAdapter(productsViewModel.products, requireActivity().application)

        binding.productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = productsAdapter
        }


        //filter chips
        binding.chipTees.setOnClickListener {
            productsAdapter.showListByCategory(getCheckedChips(binding.chipGroup.checkedChipIds))
        }

        binding.chipAll.setOnClickListener {
            productsAdapter.showListByCategory(getCheckedChips(binding.chipGroup.checkedChipIds))
        }

        binding.chipBlazers.setOnClickListener {
            productsAdapter.showListByCategory(getCheckedChips(binding.chipGroup.checkedChipIds))
        }

        binding.chipJackets.setOnClickListener {
            productsAdapter.showListByCategory(getCheckedChips(binding.chipGroup.checkedChipIds))
        }


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getCheckedChips(ids: List<Int>):MutableList<String>{
        var resultList: MutableList<String> = mutableListOf()
        for(id in ids){
            if(binding.chipTees.id.toString() == id.toString() ){
                resultList.add(binding.chipTees.text.toString())
            }
            if(binding.chipJackets.id == id){
                resultList.add(binding.chipJackets.text.toString())
            }
            if(binding.chipAll.id == id){
                resultList.add(binding.chipAll.text.toString())
            }
            if(binding.chipBlazers.id == id){
                resultList.add(binding.chipBlazers.text.toString())
            }
        }
        return resultList
    }
}