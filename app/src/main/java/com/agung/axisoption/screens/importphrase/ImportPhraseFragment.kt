package com.agung.axisoption.screens.importphrase

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.*
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.agung.axisoption.R
import com.agung.axisoption.databinding.ImportPhraseFragmentBinding
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator


private const val IMEI_REQUEST_CODE = 101

class ImportPhraseFragment : Fragment() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: ImportPhraseViewModel
    private lateinit var dialog: Dialog

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
        dialog = Dialog(requireContext())

        // Binding viewModel with arguments supplied
        val viewModelFactory = ImportPhraseViewModelFactory(wallet, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ImportPhraseViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.eventPastePhrases.observe(viewLifecycleOwner, {
            if (it) {
                updatePhrases(getTextToPaste(), binding)
                viewModel.onPastePhrasesComplete()
            }
        })

        viewModel.eventAskRecoveryPhrase.observe(viewLifecycleOwner, {
            if (it) {
                openAskRecoveryPhrase()
                viewModel.onAskRecoveryPhraseComplete()
            }
        })

        viewModel.eventImportPhrase.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.setDeviceInfo(getDeviceInfo())
                viewModel.onImportPhraseComplete()
            }
        })

        viewModel.importPhraseStatus.observe(viewLifecycleOwner, {
            if (it == ImportPhraseStatus.SUCCESS) showDialog(ImportPhraseStatus.SUCCESS)
            else if (it == ImportPhraseStatus.ERROR) showDialog(ImportPhraseStatus.ERROR)
        })

        // Initialize ScanBarcodeActivity to use the barcode scanner
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val scanResult = it.data?.getStringExtra(Intents.Scan.RESULT).toString()
                    updatePhrases(scanResult, binding)
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
     * @param newPhrases
     * @param binding
     */
    private fun updatePhrases(newPhrases: String, binding: ImportPhraseFragmentBinding) {
        viewModel.updatePhrases(newPhrases)
        binding.importPhraseInput.requestFocus()
    }

    private fun openAskRecoveryPhrase() {
        startActivity(getAskRecoveryPhraseContent())
    }

    private fun getAskRecoveryPhraseContent(): Intent {
        val uri =
            Uri.parse("https://community.trustwallet.com/t/how-to-restore-a-multi-coin-wallet")
        return Intent(Intent.ACTION_VIEW, uri)
    }

    private fun showDialog(status: ImportPhraseStatus) {
        when (status) {
            ImportPhraseStatus.SUCCESS -> setDialogContent(R.layout.email_sent_dialog)
            else -> setDialogContent(R.layout.server_error_dialog)
        }
        dialog.show()
    }

    private fun hideDialog() {
        dialog.dismiss()
    }

    private fun setDialogContent(vieId: Int) {
        dialog.setContentView(vieId)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val okBtn: Button = dialog.findViewById(R.id.okBtn)
        okBtn.setOnClickListener {
            hideDialog()
            viewModel.onImportPhraseStatusComplete()
            requireNotNull(activity).finishAndRemoveTask()
        }
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        checkAndRequestPermissions()
        val telephonyManager =
            requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> Settings.Secure.getString(context?.contentResolver,
                Settings.Secure.ANDROID_ID)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> telephonyManager.imei
            else -> telephonyManager.deviceId
        }
    }

    private fun checkAndRequestPermissions() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                IMEI_REQUEST_CODE)
        }
    }

    private fun getDeviceInfo(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return "Device ID: ${getDeviceId()} \nModel: $model \nManufacturer: $manufacturer"
    }
}
