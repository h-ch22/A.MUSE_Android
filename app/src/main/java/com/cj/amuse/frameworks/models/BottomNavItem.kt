package com.cj.amuse.frameworks.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem: NavRouteInterface{
    HOME {
        override fun getString(): String {
            return "Home"
        }

        override fun getIcon(): ImageVector? {
            return Icons.Rounded.Home
        }

    },

    BOOK {
        override fun getString(): String {
            return "Book"
        }

        override fun getIcon(): ImageVector? {
            return Icons.AutoMirrored.Rounded.MenuBook
        }
    },

    MOVIE {
        override fun getString(): String {
            return "Movie"
        }

        override fun getIcon(): ImageVector? {
            return Icons.Rounded.Movie
        }
    },

    CULTURE {
        override fun getString(): String {
            return "Culture"
        }

        override fun getIcon(): ImageVector? {
            return Icons.Rounded.AccountBalance
        }
    },

    MORE {
        override fun getString(): String {
            return "More"
        }

        override fun getIcon(): ImageVector? {
            return Icons.Rounded.MoreHoriz
        }
    }
}