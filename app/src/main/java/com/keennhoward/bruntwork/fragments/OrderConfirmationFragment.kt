package com.keennhoward.bruntwork.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.keennhoward.bruntwork.databinding.FragmentOrderConfirmationBinding
import com.keennhoward.bruntwork.fragments.OrderConfirmationFragmentArgs
import com.keennhoward.bruntwork.fragments.OrderConfirmationFragmentDirections

class OrderConfirmationFragment : Fragment() {
    private var _binding: FragmentOrderConfirmationBinding? = null
    private val binding get() = _binding!!
    val args: OrderConfirmationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderConfirmationBinding.inflate(inflater, container, false)
        val view = binding.root

        //set order number text from safe args string
        binding.orderNumber.text = "#${args.orderId}"

        //navigate back to Products Fragment on click
        binding.ocReturnToProducts.setOnClickListener {
            navigateToProductsFragment(view)
        }

        //on back pressed handler
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToProductsFragment(view)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        return view

    }

    //Navigate to products fragment clearing back stack
    private fun navigateToProductsFragment(view: View) {
        Navigation.findNavController(view)
            .navigate(OrderConfirmationFragmentDirections.actionOrderConfirmationFragmentToProductsFragment())
    }
}