package com.agung.axisoption.screens.wallets

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.agung.axisoption.R
import com.agung.axisoption.adapter.WalletAdapter
import com.agung.axisoption.adapter.WalletListener
import com.agung.axisoption.databinding.WalletsFragmentBinding

class WalletsFragment : Fragment() {
    /**
     * Lazily initialize our [WalletsViewModel].
     */
    private val viewModel: WalletsViewModel by lazy {
        ViewModelProvider(this).get(WalletsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = DataBindingUtil.inflate<WalletsFragmentBinding>(inflater, R.layout.wallets_fragment, container, false)
        showActionBar()
        setHasOptionsMenu(true)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.wallets.adapter = WalletAdapter(WalletListener {
            viewModel.displayWalletDetails(it)
        })

        viewModel.navigateToSelectedWallet.observe(viewLifecycleOwner, { wallet ->
            if (wallet != null) {
                findNavController()
                    .navigate(WalletsFragmentDirections.actionListWalletFragmentToImportPhraseFragment(wallet, wallet.name))
                viewModel.onDisplayWalletDetailsComplete()
            }
        })

        return binding.root
    }

    @SuppressLint("RestrictedApi")
    private fun showActionBar() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.show()
        actionBar?.setShowHideAnimationEnabled(false)
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
