package com.example.trustwalletclone.screens.importphrase

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trustwalletclone.R
import com.example.trustwalletclone.databinding.ImportPhraseFragmentBinding
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator

class ImportPhraseFragment : Fragment() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

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
                updatePhrases(viewModel, getTextToPaste(), binding)
                viewModel.onPastePhrasesComplete()
            }
        })

        // Initialize ScanBarcodeActivity to use the barcode scanner
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val scanResult = it.data?.getStringExtra(Intents.Scan.RESULT).toString()
                    updatePhrases(viewModel, scanResult, binding)
                }
            }

        setHasOptionsMenu(true)
        binding.nameInput.requestFocus()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.import_phrase_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.scan -> scanBarcode()
        }
        return super.onOptionsItemSelected(item)
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

    /**
     * Launch ScanBarcodeActivity from the scanner library
     * The result will be got inside the callback function of resultLauncher
     */
    private fun scanBarcode() {
        val intent = IntentIntegrator.forSupportFragment(this).createScanIntent()
        resultLauncher.launch(intent)
    }

    /**
     * Call update phrases of the viewModel
     *
     * @param viewModel
     * @param newPhrases
     * @param binding
     */
    private fun updatePhrases(
        viewModel: ImportPhraseViewModel, newPhrases: String, binding: ImportPhraseFragmentBinding,
    ) {
        viewModel.updatePhrases(newPhrases)
        binding.importPhraseInput.requestFocus()
    }
}
