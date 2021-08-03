package com.agung.axisoption.screens.walkthrough

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.agung.axisoption.R
import com.agung.axisoption.adapter.WalkthroughAdapter
import com.agung.axisoption.databinding.WalkthroughFragmentBinding
import com.agung.axisoption.transformer.DepthPageTransformer

class WalkthroughFragment : Fragment() {
    private lateinit var viewModel: WalkthroughViewModel
    private lateinit var dialog: Dialog
    private lateinit var walkthroughAdapter: WalkthroughAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = DataBindingUtil.inflate<WalkthroughFragmentBinding>(inflater,
            R.layout.walkthrough_fragment,
            container,
            false)
        hideActionBar()
        viewModel = ViewModelProvider(this).get(WalkthroughViewModel::class.java)
        binding.viewModel = viewModel

        val viewPager = binding.viewpager
        val indicator = binding.indicator
        dialog = Dialog(requireContext())
        walkthroughAdapter = WalkthroughAdapter()

        viewPager.adapter = walkthroughAdapter
        viewPager.setPageTransformer(DepthPageTransformer())
        indicator.setViewPager(viewPager)
        walkthroughAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        viewModel.walkthroughItems.observe(viewLifecycleOwner, {
            it.let {
                walkthroughAdapter.submitList(it)
            }
        })

        viewModel.eventCreateNewWallet.observe(viewLifecycleOwner, { createNewWallet ->
            if (createNewWallet) {
                showErrorDialog()
                viewModel.onCreateWalletComplete()
            }
        })

        viewModel.eventAlreadyHaveWallet.observe(viewLifecycleOwner, { alreadyHaveWallet ->
            if (alreadyHaveWallet) {
                findNavController().navigate(WalkthroughFragmentDirections.actionWalkthroughFragmentToListWalletFragment())
                viewModel.onAlreadyHaveWalletComplete()
            }
        })

        return binding.root
    }

    private fun hideActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    private fun showErrorDialog() {
        setErrorDialogContent()
        dialog.show()
    }

    private fun hideErrorDialog() {
        dialog.dismiss()
    }

    private fun setErrorDialogContent() {
        dialog.setContentView(R.layout.server_error_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val okBtn: Button = dialog.findViewById(R.id.okBtn)
        okBtn.setOnClickListener {
            hideErrorDialog()
        }
    }
}