package com.shenawynkov.tododemo.utils

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


object BindingAdapters {


    @BindingAdapter("date")
    @JvmStatic
    fun dateToString(textView: TextView, date: Date) {

        val format = SimpleDateFormat("dd/MM/yyy")
        textView.setText(format.format(date))

    }

    @BindingAdapter("date")
    @JvmStatic
    fun dateToString(editText: TextInputEditText, date: Date?) {
        if (date == null) {
            return
        }
        val format = SimpleDateFormat("dd/MM/yyy")
        editText.setText(format.format(date))

    }


}