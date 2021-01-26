package com.keennhoward.bruntwork.main

import android.os.Bundle
import android.text.Html
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
import com.keennhoward.bruntwork.databinding.ActivityMainBinding
import com.keennhoward.bruntwork.db.room.CartDatabase

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

        //initialize MainViewModel
        val dao = CartDatabase.getInstance(application).cartDao()
        val repository = MainRepository(dao)
        val factory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        //initialize navigation controller
        controller = Navigation.findNavController(this, R.id.fragment)

        //change actionbar title color and style
        supportActionBar!!.title = Html.fromHtml("<font color=\"black\"><b>" + getString(R.string.app_name) + "</b></font>")
        supportActionBar!!.elevation = 0f //remove elevation of actionbar


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val menuItem: MenuItem = menu!!.findItem(R.id.action_cart)
        val actionView: View = menuItem.actionView

        textCartItemCount = actionView.findViewById(
            R.id.cart_badge
        )

        //change cart badge on database changed
        mainViewModel.cart.observe(this, Observer {
            if (it.isEmpty()) { //hide badge if empty
                if (textCartItemCount.visibility != View.GONE) {
                    textCartItemCount.visibility = View.GONE
                }
            } else {
                textCartItemCount.text = it.size.toString()
                textCartItemCount.visibility = View.VISIBLE
            }
        })

        //navigate to cart fragment
        menuItem.actionView.setOnClickListener {
            controller.popBackStack(R.id.cartFragment, true) //pops back stack to fragment declared
            controller.navigate(R.id.cartFragment)
        }

        return true

    }
}