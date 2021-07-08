package com.example.trustwalletclone.screens.importphrase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trustwalletclone.model.Wallet

class ImportPhraseViewModel(wallet: Wallet, app: Application) : AndroidViewModel(app) {
    private val _selectedWallet = MutableLiveData<Wallet>()
    val selectedWallet: LiveData<Wallet>
        get() = _selectedWallet

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _phrases = MutableLiveData<String>()
    val phrases: LiveData<String>
        get() = _phrases

    init {
        _selectedWallet.value = wallet
        _name.value = "Wallet 1"
        _phrases.value = String()
    }
}
