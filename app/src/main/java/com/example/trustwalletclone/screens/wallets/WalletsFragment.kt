package com.example.trustwalletclone.screens.wallets

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.trustwalletclone.R
import com.example.trustwalletclone.databinding.WalletsFragmentBinding

class WalletsFragment : Fragment() {
    private lateinit var viewModel: WalletsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = DataBindingUtil.inflate<WalletsFragmentBinding>(inflater, R.layout.wallets_fragment, container, false)
        viewModel = ViewModelProvider(this).get(WalletsViewModel::class.java)
        return binding.root
    }

}
