package com.example.trustwalletclone.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.trustwalletclone.model.WalkthroughItem

@BindingAdapter("walkthroughImage")
fun ImageView.setWalkthroughImage(item: WalkthroughItem?) {
    item?.let {
        setImageResource(item.imageId)
    }
}

@BindingAdapter("walkthroughTitle")
fun TextView.setWalkthroughTitle(item: WalkthroughItem?) {
    item?.let {
        setText(item.titleId)
    }
}

@BindingAdapter("walkthroughDesc")
fun TextView.setWalkthroughDesc(item: WalkthroughItem?) {
    item?.let {
        setText(item.descId)
    }
}
