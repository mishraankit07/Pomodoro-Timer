package com.example.pomodorotimer

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.pomodorotimer.database.FocusTime
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)


fun convertNumericQualityToString(rating: Boolean, resources: Resources): String {
    var qualityString = resources.getString(R.string.three_ok)
    when (rating) {
       true -> "Nice"
        false -> "Bad"
    }
    return qualityString
}


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}

fun formatFocusTimes(nights: List<FocusTime>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
            if (it.endTimeMilli != it.startTimeMilli) {
                append(resources.getString(R.string.end_time))
                append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
                append(resources.getString(R.string.hours_slept))
                // Hours
                append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60 / 60}:")
                // Minutes
                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60}:")
                // Seconds
                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")
            }
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

/*fun convertDurationToFormatted(startTimeMilli: Long, endTimeMilli: Long, res: Resources): String {
    val durationMilli = endTimeMilli - startTimeMilli
    val weekdayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(startTimeMilli)
    return when {
        durationMilli < ONE_MINUTE_MILLIS -> {
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.seconds_length, seconds, weekdayString)
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.minutes_length, minutes, weekdayString)
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.hours_length, hours, weekdayString)
        }
    }*/
