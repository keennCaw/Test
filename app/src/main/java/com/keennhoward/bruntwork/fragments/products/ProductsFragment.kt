package com.keennhoward.bruntwork.fragments.products

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.databinding.CustomToastBinding
import com.keennhoward.bruntwork.databinding.FragmentProductsBinding
import com.keennhoward.bruntwork.model.Product
import com.keennhoward.bruntwork.db.room.CartDatabase
import com.keennhoward.bruntwork.db.room.CartProductModel

class ProductsFragment : Fragment(), ProductsAdapter.ItemClickListener {

    private var _binding: FragmentProductsBinding? = null

    private var parent:View? = null

    private lateinit var productsViewModel: ProductsViewModel

    private val binding get() = _binding!!

    //setHasOptions menu to false so action bar does not get updated on fragment change
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductsBinding.inflate(inflater, container, false)

        parent = inflater.inflate(R.layout.fragment_products, container,false)

        //initialize fragment adapter for recyclerview
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


        //filter chips based on chips on getCheckedChips function
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

    //get all checked chips from UI
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

    //click listener from adapter class which passes product
    override fun onAddProductClickListener(product: Product.ProductData) {
        //map ProductData to cartProductModel
        val cartProductModel = CartProductModel(
            id = 0,
            product_id = product.id,
            name = product.name,
            category = product.category,
            price = product.price,
            bgColor = product.bgColor
        )
        //insert to DB
        productsViewModel.insert(cartProductModel)

        //Temporary Toast Message
        showToastMessage(product.name, product.bgColor)
    }


    private fun showToastMessage(productName:String, bgColor: String){
        val layout = layoutInflater.inflate(R.layout.custom_toast, parent!!.findViewById(R.id.toast_layout))
        val message = "<b>$productName</b> has been added to your cart"

        layout.findViewById<TextView>(R.id.toast_message).text = Html.fromHtml(message)
        layout.findViewById<ImageView>(R.id.toast_background).setBackgroundColor(bgColor.toColorInt())

        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.view = layout
        toast.show()
    }
}