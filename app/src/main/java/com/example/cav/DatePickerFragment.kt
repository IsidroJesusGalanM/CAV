package com.example.cav

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment(val listener:(day:Int,month:Int,year:Int) -> Unit): DialogFragment(),
    DatePickerDialog.OnDateSetListener{

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        listener(day,month,year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val picker = DatePickerDialog(activity as Context,R.style.datePickerTheme,this,year,month,day)

        picker.datePicker.minDate = c.timeInMillis

        picker.setOnShowListener { dialog ->
            val positiveButton = (dialog as DatePickerDialog).getButton(DatePickerDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(activity as Context, R.color.black))
            val negativeButton = (dialog as DatePickerDialog).getButton(DatePickerDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(ContextCompat.getColor(activity as Context, R.color.black))
        }

        return picker
    }


}