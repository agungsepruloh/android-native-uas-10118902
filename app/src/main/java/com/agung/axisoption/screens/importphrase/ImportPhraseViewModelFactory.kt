package com.agung.axisoption.screens.importphrase

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.agung.axisoption.model.Wallet

/**
 * Simple ViewModel factory that provides the Wallet and context to the ViewModel.
 */
class ImportPhraseViewModelFactory(
    private val wallet: Wallet,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImportPhraseViewModel::class.java)) {
            return ImportPhraseViewModel(wallet, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
