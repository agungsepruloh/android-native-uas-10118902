package com.agung.trustwalletclone.util

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agung.trustwalletclone.R
import com.agung.trustwalletclone.adapter.WalletAdapter
import com.agung.trustwalletclone.model.WalkthroughItem
import com.agung.trustwalletclone.model.Wallet
import com.agung.trustwalletclone.screens.importphrase.ImportPhraseStatus
import com.agung.trustwalletclone.screens.wallets.WalletsApiStatus

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

@BindingAdapter("walletsApiStatus")
fun bindStatus(statusImageView: ImageView, status: WalletsApiStatus?) {
    when (status) {
        WalletsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        WalletsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        WalletsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("walletsApiStatusText")
fun bindStatusText(statusTextView: TextView, status: WalletsApiStatus?) {
    when (status) {
        WalletsApiStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
        }
        else -> {
            statusTextView.visibility = View.GONE
        }
    }
}

@BindingAdapter("importPhraseStatus")
fun Button.setImportPhraseStatus(status: ImportPhraseStatus?) {
    status.let {
        isEnabled = when (status) {
            ImportPhraseStatus.LOADING -> false
            else -> true
        }
    }
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
