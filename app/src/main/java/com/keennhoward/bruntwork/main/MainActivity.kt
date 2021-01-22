package com.keennhoward.bruntwork.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.keennhoward.bruntwork.R
import com.keennhoward.bruntwork.model.Product
import com.keennhoward.bruntwork.room.CartDatabase
import com.keennhoward.bruntwork.room.CartProductModel

private var cartCount: Int = 10
private lateinit var textCartItemCount: TextView
private lateinit var mainViewModel: MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = CartDatabase.getInstance(application).cartDao()
        val repository = MainRepository(dao)
        val factory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

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

        return true
    }
}