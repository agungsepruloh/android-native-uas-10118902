package com.agung.axisoption.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wallet(
    var id: Long,
    var name: String,
    var imgBase64: String
) : Parcelable
