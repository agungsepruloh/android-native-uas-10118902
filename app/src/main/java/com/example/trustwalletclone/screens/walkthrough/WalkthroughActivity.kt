package com.example.trustwalletclone.screens.walkthrough

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.trustwalletclone.transformer.DepthPageTransformer
import com.example.trustwalletclone.R
import com.example.trustwalletclone.databinding.ActivityWalkthroughBinding
import com.example.trustwalletclone.adapter.WalkthroughAdapter

class WalkthroughActivity : AppCompatActivity() {
    lateinit var walkThroughAdapter: WalkthroughAdapter
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityWalkthroughBinding>(this,
            R.layout.activity_walkthrough)
        val viewModel = ViewModelProvider(this).get(WalkthroughViewModel::class.java)
        binding.viewModel = viewModel

        val viewPager = binding.viewpager
        val indicator = binding.indicator
        dialog = Dialog(this)
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

        viewModel.isShowErrorDialog.observe(this, { isShowErrorDialog ->
            if (isShowErrorDialog) {
                showErrorDialog()
                viewModel.errorDialogShowed()
            }
        })
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
