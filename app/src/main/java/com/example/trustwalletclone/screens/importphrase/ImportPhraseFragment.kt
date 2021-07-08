package com.example.trustwalletclone.screens.importphrase

import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
        binding.lifecycleOwner = this

        val application = requireNotNull(activity).application
        val wallet = ImportPhraseFragmentArgs.fromBundle(requireArguments()).wallet

        // Binding viewModel with arguments supplied
        val viewModelFactory = ImportPhraseViewModelFactory(wallet, application)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(ImportPhraseViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.eventPastePhrases.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.updatePhrases(getTextToPaste())
                viewModel.onPastePhrasesComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Get text to paste from the keyboard/clipboard
     *
     * @return textToPaste: String
     */
    private fun getTextToPaste(): String {
        val clipBoard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return (clipBoard.primaryClip?.getItemAt(0)?.text ?: "").toString()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.import_phrase_menu, menu)
    }
}
