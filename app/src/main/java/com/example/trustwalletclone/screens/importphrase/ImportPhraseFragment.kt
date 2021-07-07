package com.example.trustwalletclone.screens.importphrase

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.trustwalletclone.R
import com.example.trustwalletclone.databinding.ImportPhraseFragmentBinding

class ImportPhraseFragment : Fragment() {
    private lateinit var viewModel: ImportPhraseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = DataBindingUtil.inflate<ImportPhraseFragmentBinding>(inflater, R.layout.import_phrase_fragment, container, false)
        val wallet = ImportPhraseFragmentArgs.fromBundle(requireArguments()).wallet
        viewModel = ViewModelProvider(this).get(ImportPhraseViewModel::class.java)
        Log.d("selectedWallet", wallet.name)
        return binding.root
    }
}
