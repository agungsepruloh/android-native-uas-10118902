package com.example.trustwalletclone.screens.importphrase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trustwalletclone.model.Wallet

class ImportPhraseViewModel(wallet: Wallet, app: Application) : AndroidViewModel(app) {
    private val _selectedWallet = MutableLiveData<Wallet>()
    val selectedWallet: LiveData<Wallet>
        get() = _selectedWallet

    // Two-way databinding, exposing MutableLiveData
    val name = MutableLiveData<String>()
    val phrases = MutableLiveData<String>()

    init {
        _selectedWallet.value = wallet
        name.value = "Wallet 1"
    }

    fun importPhrases() {
        Log.d("ImportPhraseViewModel", name.value ?: "")
        Log.d("ImportPhraseViewModel", phrases.value ?: "")
    }
}
