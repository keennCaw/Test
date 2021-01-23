package com.keennhoward.bruntwork.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.cart.CartFragment
import com.keennhoward.bruntwork.databinding.ActivityMainBinding
import com.keennhoward.bruntwork.room.CartDatabase

private var check = false
private lateinit var textCartItemCount: TextView
private lateinit var mainViewModel: MainViewModel

private lateinit var binding: ActivityMainBinding

private lateinit var controller: NavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dao = CartDatabase.getInstance(application).cartDao()
        val repository = MainRepository(dao)
        val factory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        controller = Navigation.findNavController(this, R.id.fragment)
    }


     private val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->

         if(check){
             Log.d("destination", textCartItemCount.text.toString())
         }
     }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val menuItem: MenuItem = menu!!.findItem(R.id.action_cart)

        val actionView: View = menuItem.actionView

        textCartItemCount = actionView.findViewById(
            R.id.cart_badge
        )

        mainViewModel.cart.observe(this, Observer {
            if(it.isEmpty()){
                if (textCartItemCount.visibility != View.GONE) {
                    textCartItemCount.visibility = View.GONE
                    }
                }else{
                textCartItemCount.text = it.size.toString()
                textCartItemCount.visibility = View.VISIBLE
            }
        })

        //navigate to cart fragment
        menuItem.actionView.setOnClickListener {
        Log.d("options", "menu")
            controller.navigate(R.id.cartFragment)
        }

        check = true
        return true
    }

    override fun onPause() {
        Log.d("pause", "trigger")
        super.onPause()
        controller.removeOnDestinationChangedListener(listener)
    }

    override fun onResume() {
        super.onResume()
        Log.d("pause", "trigger")
        controller.addOnDestinationChangedListener(listener)
    }
}