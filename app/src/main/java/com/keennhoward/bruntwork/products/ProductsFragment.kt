package com.keennhoward.bruntwork.products

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.model.Utils.getJsonDataFromAsset
import com.keennhoward.bruntwork.databinding.FragmentProductsBinding
import com.keennhoward.bruntwork.model.Product
import com.keennhoward.bruntwork.room.CartDatabase
import com.keennhoward.bruntwork.room.CartProductModel

class ProductsFragment : Fragment(), ProductsAdapter.ItemClickListener {

    private var _binding: FragmentProductsBinding? = null

    private lateinit var productsViewModel: ProductsViewModel

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuItem: MenuItem = menu!!.findItem(R.id.action_cart)
        menuItem.actionView.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_productsFragment_to_cartFragment)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val view = binding.root

        val dao = CartDatabase.getInstance(requireActivity().application).cartDao()
        val repository = ProductsRepository(dao)
        val factory = ProductsViewModelFactory(repository, requireActivity().application)

        productsViewModel =
            ViewModelProvider(requireActivity(), factory).get(ProductsViewModel::class.java)

        val productsAdapter =
            ProductsAdapter(productsViewModel.products, requireActivity().application, this)

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


    private fun getCheckedChips(ids: List<Int>): MutableList<String> {
        var resultList: MutableList<String> = mutableListOf()
        for (id in ids) {
            if (binding.chipTees.id.toString() == id.toString()) {
                resultList.add(binding.chipTees.text.toString())
            }
            if (binding.chipJackets.id == id) {
                resultList.add(binding.chipJackets.text.toString())
            }
            if (binding.chipAll.id == id) {
                resultList.add(binding.chipAll.text.toString())
            }
            if (binding.chipBlazers.id == id) {
                resultList.add(binding.chipBlazers.text.toString())
            }
        }
        return resultList
    }

    override fun onAddProductClickListener(product: Product.ProductData) {
        val cartProductModel = CartProductModel(
            id = 0,
            product_id = product.id,
            name = product.name,
            category = product.category,
            price = product.price,
            bgColor = product.bgColor
        )
        productsViewModel.insert(cartProductModel)
        Toast.makeText(
            requireActivity(),
            "Added: ${cartProductModel.name} to Cart",
            Toast.LENGTH_SHORT
        )
        Log.d("product", product.name)
    }
}