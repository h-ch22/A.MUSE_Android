package com.cj.amuse.frameworks.models

import android.content.Context

interface AlertContentsInterface {
    fun getTitle(context: Context): String
    fun getMessage(context: Context): String
}