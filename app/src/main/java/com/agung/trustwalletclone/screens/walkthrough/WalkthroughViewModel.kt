package com.agung.trustwalletclone.screens.walkthrough

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agung.trustwalletclone.R
import com.agung.trustwalletclone.model.WalkthroughItem

class WalkthroughViewModel : ViewModel() {
    private val _walkthroughItems = MutableLiveData<List<WalkthroughItem>>()
    val walkthroughItems: LiveData<List<WalkthroughItem>>
        get() = _walkthroughItems

    private val _eventCreateNewWallet = MutableLiveData<Boolean>()
    val eventCreateNewWallet: LiveData<Boolean>
        get() = _eventCreateNewWallet

    private val _eventAlreadyHaveWallet = MutableLiveData<Boolean>()
    val eventAlreadyHaveWallet: LiveData<Boolean>
        get() = _eventAlreadyHaveWallet

    init {
        _walkthroughItems.value = listOf(
            WalkthroughItem(1,
                R.drawable.icon_private_secure,
                R.string.private_and_secure,
                R.string.private_keys_never_leave_your_device),
            WalkthroughItem(2,
                R.drawable.icon_all_assets,
                R.string.all_assets_in_one_place,
                R.string.view_and_store_your_assets_seamlessly),
            WalkthroughItem(3,
                R.drawable.icon_trade_assets,
                R.string.trade_assets,
                R.string.trade_your_assets_anonymously),
            WalkthroughItem(4,
                R.drawable.icon_explore,
                R.string.explore_decentralized_apps,
                R.string.earn_explore_utilize_spend_trade_and_more),
        )

        _eventCreateNewWallet.value = false
        _eventAlreadyHaveWallet.value = false
    }

    fun createNewWallet() {
        _eventCreateNewWallet.value = true
    }

    fun onCreateWalletComplete() {
        _eventCreateNewWallet.value = false
    }

    fun alreadyHaveWallet() {
        _eventAlreadyHaveWallet.value = true
    }

    fun onAlreadyHaveWalletComplete() {
        _eventAlreadyHaveWallet.value = false
    }
}