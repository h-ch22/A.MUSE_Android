package com.cj.amuse.userManagement.models

import android.content.Context
import com.cj.amuse.R
import com.cj.amuse.frameworks.models.AlertContentsInterface


enum class UserManagementAlertTypeModel: AlertContentsInterface {
    EMPTY_FIELD {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_ALERT_TITLE_OF_EMPTY_FIELD)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_EMPTY_FIELD)
        }
    },
    ACCOUNT_DOES_NOT_EXISTS {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_ALERT_TITLE_OF_ACCOUNT_DOES_NOT_EXISTS)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_ACCOUNT_DOES_NOT_EXISTS)
        }
    },
    INVALID_EMAIL_TYPE {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_ALERT_TITLE_OF_INVALID_EMAIL_TYPE)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_INVALID_EMAIL_TYPE)
        }
    },
    WEAK_PASSWORD {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_ALERT_TITLE_OF_WEAK_PASSWORD)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_WEAK_PASSWORD)
        }
    },
    PASSWORD_MISMATCH {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_ALERT_TITLE_OF_PASSWORD_MISMATCH)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_PASSWORD_MISMATCH)
        }
    },
    LICENSE_NOT_ACCEPTED {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_ALERT_TITLE_OF_LICENSE_NOT_ACCEPTED)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_LICENSE_NOT_ACCEPTED)
        }
    },
    SIGN_IN_ERROR {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_ERROR)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_SIGN_IN_ERROR)
        }
    },
    SIGN_UP_ERROR {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_ERROR)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_SIGN_UP_ERROR)
        }

    },
    SENT_PASSWORD_RESET_MAIL {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.TXT_DONE)
        }

        override fun getMessage(context: Context): String {
            return context.getString(R.string.TXT_ALERT_MESSAGE_OF_SENT_PASSWORD_RESET_MAIL)
        }
    }
}