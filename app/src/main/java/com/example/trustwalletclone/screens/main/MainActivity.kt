package com.example.trustwalletclone.screens.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.trustwalletclone.R
import com.example.trustwalletclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main)
        showActionBar()
    }

    private fun showActionBar() {
        val actionBar: ActionBar? = supportActionBar
        // Show back button in the action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        val currentDestination = navController.currentDestination

        when (item.itemId) {
            android.R.id.home -> {
                // Check if the current fragment is the root fragment or not
                // if the current fragment is a root fragment / start destination
                // then finish the activity
                // otherwise it will popup / navigate up the fragment
                when (currentDestination?.id) {
                    R.id.listWalletFragment -> {
                        finish()
                        return true
                    }
                    else -> navController.navigateUp()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
