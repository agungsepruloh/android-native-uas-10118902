package com.example.trustwalletclone.screens.importphrase

import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.trustwalletclone.R
import com.example.trustwalletclone.databinding.ImportPhraseFragmentBinding

class ImportPhraseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = DataBindingUtil.inflate<ImportPhraseFragmentBinding>(inflater,
            R.layout.import_phrase_fragment,
            container,
            false)
        val application = requireNotNull(activity).application
        val wallet = ImportPhraseFragmentArgs.fromBundle(requireArguments()).wallet

        // Binding viewModel with arguments supplied
        val viewModelFactory = ImportPhraseViewModelFactory(wallet, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(ImportPhraseViewModel::class.java)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.import_phrase_menu, menu)
    }
}
