package com.helrin.utillib

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    enum class Type(var value: String) {
        YYYY_MM_DD_BLANK("yyyy MM dd"),
        YYYY_MM_DD_DASH("yyyy-MM-dd"),
        YYYY_MM_DASH("yyyy-MM"),
        YYYY_MM_POINT("yyyy.MM"),
        YYYY_MM_DD_POINT("yyyy.MM.dd"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        YY_MM_DD_HH_MM_SS_DASH("yy-MM-dd HH:mm:ss"),
        SERVER_TIMEZONE("yyyy-MM-dd'T'HH:mm:ssZZZZZ");

        fun get(): String {
            return value
        }
    }

    private lateinit var mSDF: SimpleDateFormat
    private var mSDFPattern: String? = null
    private var mResult: CharSequence? = null

    fun from(date: Date): DateFormatter {
        return DateFormatter().withDate(date)
    }

    fun from(date: Long): DateFormatter {
        val cal = Calendar.getInstance()
        cal.timeInMillis = date
        return DateFormatter().withDate(cal.time)
    }

    fun from(date: String): DateFormatter {
        return DateFormatter().withDate(date)
    }

    private fun withDate(date: Date): DateFormatter {
        init(date)
        return this
    }

    private fun withDate(string: String): DateFormatter {
        init(string)
        return this
    }

    private fun init(string: String?) {
        if (string == null) return

        mResult = string

        mSDFPattern = when {
            Regex("""\d{4}-\d{2}-\d{2}""").matches(string) -> "yyyy-MM-dd"
            Regex("""\d{8}""").matches(string) -> "yyyyMMdd"
            Regex("""\d{4}.\d{2}.\d{2}""").matches(string) -> "yyyy.MM.dd"
            Regex("""\d{4}-\d{2}-\d{2}""").matches(string) -> "yyyy-MM-dd"
            Regex("""\d{4}.\d{2}""").matches(string) -> "yyyy.MM"
            Regex("""\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}""").matches(string) -> "yyyy-MM-dd HH:mm:ss"
            else ->{
                L.e("init","들어온 날짜 = $string")
                "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
            }
        }

        mSDF = SimpleDateFormat(mSDFPattern, Locale.getDefault())
    }

    private fun init(date: Date) {
        mSDFPattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        mResult = SimpleDateFormat(mSDFPattern, Locale.getDefault()).format(date)
        mSDF = SimpleDateFormat(mSDFPattern, Locale.getDefault())
    }

    fun from(year: Int, month: Int, dayOfMonth: Int): DateFormatter {
        val df = DateFormatter()

        //        mToUTC = true;
        var sYear = year.toString()
        var sMonth = (month + 1).toString()
        var sDay = dayOfMonth.toString()
        if (sYear.length == 1) sYear = "0$sYear"
        if (sMonth.length == 1) sMonth = "0$sMonth"
        if (sDay.length == 1) sDay = "0$sDay"
        df.mResult = StringBuilder()
            .append(sYear)
            .append(sMonth)
            .append(sDay)
        val iYear = StringBuilder()
        val iMonth = StringBuilder()
        val iDay = StringBuilder()
        var yearLength = sYear.length
        var monthLength = sMonth.length
        var dayLength = sDay.length
        while (yearLength-- > 0) {
            iYear.append("y")
        }
        while (monthLength-- > 0) {
            iMonth.append("M")
        }
        while (dayLength-- > 0) {
            iDay.append("d")
        }
        df.mSDF = SimpleDateFormat(
            iYear.toString() + iMonth.toString() + iDay.toString(),
            Locale.getDefault()
        )
        return df
    }

    fun from(hourOfDay: Int, minute: Int): DateFormatter {
        val df = DateFormatter()

//        mToUTC = true;
        var sHour = hourOfDay.toString()
        var sMinute = minute.toString()
        if (sHour.length == 1) sHour = "0$sHour"
        if (sMinute.length == 1) sMinute = "0$sMinute"
        df.mResult = StringBuilder().append(sHour).append(sMinute)
        val iHour = StringBuilder()
        val iMinute = StringBuilder()
        var hourLength = sHour.length
        var minuteLength = sMinute.length
        while (hourLength-- > 0) {
            iHour.append("H")
        }
        while (minuteLength-- > 0) {
            iMinute.append("m")
        }
        df.mSDF = SimpleDateFormat(iHour.toString() + iMinute.toString(), Locale.getDefault())
        return df
    }

    private var mFromUTC: Boolean? = null
    fun fromUTC(): DateFormatter? {
        mFromUTC = true
        return this
    }

    private var mToUTC: Boolean? = null
    fun toUTC(): DateFormatter? {
        mToUTC = true
        return this
    }

    fun to(type: Type?): String? {
        val sdf = SimpleDateFormat(type?.get() ?: Type.SERVER_TIMEZONE.get(), Locale.getDefault())
        if (mFromUTC != null) mSDF.timeZone = TimeZone.getTimeZone("UTC")
        if (mToUTC != null) sdf.timeZone = TimeZone.getTimeZone("UTC")
        var result: String? = ""
        try {
            result = if (mResult != null) {
                sdf.format(mSDF.parse(mResult.toString()))
            } else {
                sdf.format(Calendar.getInstance().time)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }

    fun toDate(): Date {
        var result = Date()
        try {
            result = mSDF.parse(mResult.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }
}
