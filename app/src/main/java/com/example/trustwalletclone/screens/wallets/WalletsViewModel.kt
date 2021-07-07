package com.example.trustwalletclone.screens.wallets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustwalletclone.model.Wallet
import com.example.trustwalletclone.network.WalletApi
import kotlinx.coroutines.launch

class WalletsViewModel : ViewModel() {
    private val _wallets = MutableLiveData<List<Wallet>>()
    val wallets: LiveData<List<Wallet>>
        get() = _wallets

    init {
        viewModelScope.launch {
            try {
                _wallets.value = WalletApi.retrofitService.getWallets()
                Log.d("wallets", "success")
            } catch (e: Exception) {
                _wallets.value = ArrayList()
                Log.d("wallets", e.toString())
            }
        }
    }
}
