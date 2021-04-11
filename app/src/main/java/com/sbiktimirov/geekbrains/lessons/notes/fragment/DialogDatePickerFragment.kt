package com.sbiktimirov.geekbrains.lessons.notes.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import android.text.format.DateFormat
import java.util.*

private const val DATE_KEY = "date_key"

class DialogDatePickerFragment private constructor() : DialogFragment() {
    private var handleDateChanged: (Date) -> Unit = {}
    private lateinit var dialogMode: EnumDialogDatePickerMode

    private constructor(date: Date) : this() {
        arguments = Bundle().apply {
            putSerializable(DATE_KEY, date)
        }
    }

    constructor(
        date: Date,
        dialogMode: EnumDialogDatePickerMode,
        fragment: Fragment,
        handleDateChanged: (Date) -> Unit
    ) : this(date) {
        this.handleDateChanged = handleDateChanged
        this.dialogMode = dialogMode
        setTargetFragment(fragment, 0)
        show(
            fragment.parentFragmentManager,
            "DialogDatePickerFragment"
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(DATE_KEY) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        if (dialogMode == EnumDialogDatePickerMode.DATE) {
            val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                handleDateChanged(GregorianCalendar(year, month, dayOfMonth).time)
            }

            return DatePickerDialog(
                requireContext(),
                dateListener,
                year,
                month,
                day
            )
        } else {
            val timeChangeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                handleDateChanged(calendar.time)
            }

            return TimePickerDialog(
                requireContext(),
                timeChangeListener,
                hour,
                minute,
                DateFormat.is24HourFormat(requireContext())
            )
        }
    }
}

enum class EnumDialogDatePickerMode {
    DATE, TIME
}