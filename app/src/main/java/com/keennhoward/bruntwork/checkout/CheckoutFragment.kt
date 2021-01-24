package com.keennhoward.bruntwork.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.cart.CartRepository
import com.keennhoward.bruntwork.cart.CartViewModel
import com.keennhoward.bruntwork.cart.CartViewModelFactory
import com.keennhoward.bruntwork.databinding.FragmentCartBinding
import com.keennhoward.bruntwork.databinding.FragmentCheckoutBinding
import com.keennhoward.bruntwork.db.room.CartDatabase

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var checkoutViewModel: CheckoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding
        _binding = FragmentCheckoutBinding.inflate(inflater, container,false)
        val view = binding.root

        val dao = CartDatabase.getInstance(requireActivity().application).cartDao()
        val repository = CheckoutRepository(dao)
        val factory = CheckoutViewModelFactory(repository)
        checkoutViewModel = ViewModelProvider(requireActivity(),factory).get(CheckoutViewModel::class.java)

        binding.checkoutButton.setOnClickListener {
            
        }
        return view
    }
}