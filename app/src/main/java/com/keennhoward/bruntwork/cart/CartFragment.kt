package com.keennhoward.bruntwork.cart

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.databinding.FragmentCartBinding
import com.keennhoward.bruntwork.databinding.FragmentProductsBinding
import com.keennhoward.bruntwork.products.ProductsRepository
import com.keennhoward.bruntwork.room.CartDatabase
import com.keennhoward.bruntwork.room.CartProductModel


class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private lateinit var cartViewModel: CartViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container,false)
        val view = binding.root

        val dao = CartDatabase.getInstance(requireActivity().application).cartDao()
        val repository = CartRepository(dao)
        val factory = CartViewModelFactory(repository)

        val cartAdapter = CartAdapter()


        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = cartAdapter
        }

        cartViewModel = ViewModelProvider(requireActivity(),factory).get(CartViewModel::class.java)

        cartViewModel.cart.observe(requireActivity(), Observer {
            cartAdapter.setListData(ArrayList(it))
            cartAdapter.notifyDataSetChanged()
        })
        //val cartAdapter = CartAdapter()


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
}