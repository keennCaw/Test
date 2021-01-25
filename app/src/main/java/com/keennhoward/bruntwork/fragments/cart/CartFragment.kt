package com.keennhoward.bruntwork.fragments.cart

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
import com.keennhoward.bruntwork.util.Utils


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
            binding.cartTotalTextView.text = "$ ${Utils.cartTotalPrice(ArrayList(it))}"

            //changes the button to go back when cart is empty
            if(context != null){ //context check when updating UI from observer
                if(it.isEmpty()){
                    binding.cartBuyNowButton.text = getString(R.string.cart_products_button)
                }else{
                    binding.cartBuyNowButton.text = getString(R.string.cart_buy_now_button)
                }
            }
        })

        binding.cartBuyNowButton.setOnClickListener {
            //navigate to checkout fragment when cart is not empty
            //navigates to products fragment when cart is empty
            if(binding.cartBuyNowButton.text == getString(R.string.cart_buy_now_button)){
                Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_checkoutFragment)
            }else{
                Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_productsFragment)
            }
        }

        return view
    }

    //cart delete click listener interface in adapter class which passes cartItem
    override fun onDeleteCartItemClickListener(cartItem: CartProductModel) {
        cartViewModel.delete(cartItem)
    }

}