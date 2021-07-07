package com.example.trustwalletclone.screens.walkthrough

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trustwalletclone.R
import com.example.trustwalletclone.model.WalkthroughItem

class WalkthroughViewModel : ViewModel() {
    private val _walkthroughItems = MutableLiveData<List<WalkthroughItem>>()
    val walkthroughItems: LiveData<List<WalkthroughItem>>
        get() = _walkthroughItems

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
    }
}
