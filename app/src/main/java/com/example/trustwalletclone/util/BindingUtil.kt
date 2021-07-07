package com.example.trustwalletclone.util

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trustwalletclone.adapter.WalletAdapter
import com.example.trustwalletclone.model.WalkthroughItem
import com.example.trustwalletclone.model.Wallet
import java.util.*

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

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Wallet>?) {
    val adapter = recyclerView.adapter as WalletAdapter
    adapter.submitList(data)
}

@BindingAdapter("walletString")
fun TextView.setWalletString(item: Wallet?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("walletImage")
fun ImageView.setWalletImage(item: Wallet?) {
    val decodedString = Base64.decode(item?.imgBase64, Base64.DEFAULT)
    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    item?.let {
        setImageBitmap(decodedByte)
    }
}

@BindingAdapter("listItem")
fun ConstraintLayout.setPaddingAndBackground(item: Wallet?) {
    val res = context.resources
    val density = context.resources.displayMetrics.density
    val oldPaddingInDp = (density * 12).toInt()
    var additionalPaddingInDp = (density * 0).toInt()
    var newPaddingInDp = oldPaddingInDp + additionalPaddingInDp
    var backgroundImage = "list_item_border_1dp"

    when (item?.name) {
        "Multi-Coin Wallet" -> {
            additionalPaddingInDp = (density * 30).toInt()
            newPaddingInDp = oldPaddingInDp + additionalPaddingInDp
            backgroundImage = "list_item_border_20dp"
        }
    }

    item.let {
        setPadding(paddingLeft, newPaddingInDp, paddingRight, newPaddingInDp)
        setBackgroundResource(res.getIdentifier(backgroundImage, "drawable", context.packageName))
    }
}
