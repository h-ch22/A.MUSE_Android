package com.cj.amuse.contents.models

import android.net.Uri

data class ContentsDataModel(
    val author: String,
    val publisher: String,
    val summary: String,
    val title: String,
    val type: ContentsTypeModel,
    val url: Uri,
    val cover: Uri,
    val score: Int
)
