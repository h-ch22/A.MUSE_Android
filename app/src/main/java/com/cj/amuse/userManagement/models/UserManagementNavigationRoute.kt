package com.cj.amuse.userManagement.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.cj.amuse.frameworks.models.NavRouteInterface


enum class UserManagementNavigationRoute: NavRouteInterface {
    SIGN_IN {
        override fun getString(): String {
            return "Sign In"
        }

        override fun getIcon(): ImageVector? {
            return null
        }
    },
    SIGN_UP {
        override fun getString(): String {
            return "Sign Up"
        }

        override fun getIcon(): ImageVector? {
            return null
        }
    },
    RESET_PASSWORD {
        override fun getString(): String {
            return "Reset Password"
        }

        override fun getIcon(): ImageVector? {
            return null
        }
    },
    SPLASH {
        override fun getString(): String {
            return "Splash"
        }

        override fun getIcon(): ImageVector? {
            return null
        }
    }
}