package com.cj.amuse.home.models

import android.net.Uri

data class BannerDataModel(
    val id: Int,
    val url: Uri,
    val title: String,
    val author: String,
    val sentence: String
)
