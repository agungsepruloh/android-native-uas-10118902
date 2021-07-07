package com.example.trustwalletclone.screens.walkthrough

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.trustwalletclone.transformer.DepthPageTransformer
import com.example.trustwalletclone.R
import com.example.trustwalletclone.databinding.ActivityWalkthroughBinding
import com.example.trustwalletclone.adapter.WalkthroughAdapter

class WalkthroughActivity : AppCompatActivity() {
    lateinit var walkThroughAdapter: WalkthroughAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityWalkthroughBinding>(this,
            R.layout.activity_walkthrough)
        val viewModel = ViewModelProvider(this).get(WalkthroughViewModel::class.java)

        val viewPager = binding.viewpager
        val indicator = binding.indicator
        walkThroughAdapter = WalkthroughAdapter()

        viewPager.adapter = walkThroughAdapter
        viewPager.setPageTransformer(DepthPageTransformer())
        indicator.setViewPager(viewPager)
        walkThroughAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        viewModel.walkthroughItems.observe(this, {
            it.let {
                walkThroughAdapter.submitList(it)
            }
        })
    }
}
