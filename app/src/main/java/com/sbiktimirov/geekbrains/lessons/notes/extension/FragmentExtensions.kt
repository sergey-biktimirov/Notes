package com.sbiktimirov.geekbrains.lessons.notes.extension

import androidx.fragment.app.Fragment
import com.sbiktimirov.geekbrains.lessons.notes.fragment.DialogDatePickerFragment
import com.sbiktimirov.geekbrains.lessons.notes.fragment.EnumDialogDatePickerMode
import java.util.*

fun Fragment.showDatePickerDialog(
    date: Date,
    handleDateChanged: (Date) -> Unit
): DialogDatePickerFragment {
    return DialogDatePickerFragment(date, EnumDialogDatePickerMode.DATE, this, handleDateChanged)
}

fun Fragment.getContextDateFormat(): java.text.DateFormat {
    return android.text.format.DateFormat.getDateFormat(requireContext())
}

fun Fragment.stringToDate(string: String): Date {
    val dateFormat: java.text.DateFormat = android.text.format.DateFormat.getDateFormat(requireContext())
    return dateFormat.parse(string) ?: Date()
}