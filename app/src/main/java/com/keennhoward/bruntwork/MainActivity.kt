package com.keennhoward.bruntwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView

private var cartCount:Int = 10
private lateinit var textCartItemCount:TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val menuItem: MenuItem = menu!!.findItem(R.id.action_cart)

        val actionView:View = menuItem.actionView

        textCartItemCount = actionView.findViewById(R.id.cart_badge)

        textCartItemCount.text = cartCount.toString()
        setupBadge()
        return true
    }

    private fun setupBadge(){
        if(cartCount != null){
            if(cartCount == 0){
                if(textCartItemCount.visibility != View.GONE){
                    textCartItemCount.visibility = View.GONE
                }else{
                    textCartItemCount.text = cartCount.toString()
                    if(textCartItemCount.visibility != View.VISIBLE){
                        textCartItemCount.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

}