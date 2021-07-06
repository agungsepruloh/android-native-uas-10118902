package com.example.trustwalletclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.trustwalletclone.databinding.ActivityWalkthroughBinding

class WalkthroughActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityWalkthroughBinding>(this, R.layout.activity_walkthrough)
    }
}