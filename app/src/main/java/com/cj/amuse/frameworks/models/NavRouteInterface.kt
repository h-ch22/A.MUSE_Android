package com.cj.amuse.frameworks.models

import androidx.compose.ui.graphics.vector.ImageVector

interface NavRouteInterface {
    fun getString(): String
    fun getIcon(): ImageVector?
}