package com.example.trustwalletclone.screens.wallets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustwalletclone.model.Wallet
import com.example.trustwalletclone.network.WalletApi
import kotlinx.coroutines.launch

enum class WalletsApiStatus { LOADING, ERROR, DONE }

class WalletsViewModel : ViewModel() {
    private val _wallets = MutableLiveData<List<Wallet>>()
    val wallets: LiveData<List<Wallet>>
        get() = _wallets

    private val _status = MutableLiveData<WalletsApiStatus>()
    val status: LiveData<WalletsApiStatus>
        get() = _status

    init {
        viewModelScope.launch {
            _status.value = WalletsApiStatus.LOADING
            try {
                _wallets.value = WalletApi.retrofitService.getWallets()
                _status.value = WalletsApiStatus.DONE
            } catch (e: Exception) {
                _wallets.value = ArrayList()
                _status.value = WalletsApiStatus.ERROR
            }
        }
    }
}
