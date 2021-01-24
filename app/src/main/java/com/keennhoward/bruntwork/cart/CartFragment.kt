package com.keennhoward.bruntwork.cart

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.databinding.FragmentCartBinding
import com.keennhoward.bruntwork.db.room.CartDatabase
import com.keennhoward.bruntwork.db.room.CartProductModel


class CartFragment : Fragment(), CartItemClickListener {

    private var _binding: FragmentCartBinding? = null

    private lateinit var cartViewModel: CartViewModel

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false) //false to not update action bar
    }


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
        val cartAdapter = CartAdapter(this)
        cartViewModel = ViewModelProvider(requireActivity(),factory).get(CartViewModel::class.java)

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = cartAdapter
        }

        //cart LiveData observer
        cartViewModel.cart.observe(requireActivity(), Observer {
            cartAdapter.submitList(ArrayList(it))
            binding.cartTotalTextView.text = "$ ${cartTotalPrice(ArrayList(it))}"
        })

        binding.cartBuyNowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_checkoutFragment)
        }

        return view
    }

    //cart delete click listener interface in adapter class which passes cartItem
    override fun onDeleteCartItemClickListener(cartItem: CartProductModel) {
        cartViewModel.delete(cartItem)
    }

    //get cart Total Price
    private fun cartTotalPrice(cart:ArrayList<CartProductModel>):Double{
        var totalPrice = 0.00

        for(cartItem in cart){
            totalPrice += cartItem.price.toDouble()
        }
        return totalPrice
    }

}