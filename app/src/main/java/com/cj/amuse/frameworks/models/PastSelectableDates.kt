package com.cj.amuse.frameworks.models

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
object PastSelectableDates: SelectableDates {
    @ExperimentalMaterial3Api
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }

    @ExperimentalMaterial3Api
    override fun isSelectableYear(year: Int): Boolean {
        return year <= LocalDate.now().year
    }
}