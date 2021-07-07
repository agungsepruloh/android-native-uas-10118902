package com.example.trustwalletclone.screens.wallets

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getInfoIntent(): Intent {
        val uri = Uri.parse("https://community.trustwallet.com/t/how-to-import-a-wallet/87")
        return Intent(Intent.ACTION_VIEW, uri)
    }

    private fun openInfo() {
        startActivity(getInfoIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.wallets_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> openInfo()
        }
        return super.onOptionsItemSelected(item)
    }

}
