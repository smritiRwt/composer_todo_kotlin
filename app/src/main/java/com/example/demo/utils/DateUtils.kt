package com.example.demo.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun getTodayDate(): String =
    LocalDate.now().format(DateTimeFormatter.ISO_DATE)

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentWeekYear(): String {
    val date = LocalDate.now()
    val weekNumber = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
    val year = date.year
    return String.format("%04d-%02d", year, weekNumber)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentMonthYear(): String =
    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
