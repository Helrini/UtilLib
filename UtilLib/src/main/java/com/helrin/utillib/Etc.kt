package com.helrin.utillib

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object Etc {
    /**
     * 객체복사
     */
    fun <T : Serializable> deepCopy(obj: T?): T? {
        if (obj == null) return null
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(obj)
        oos.close()
        val bais = ByteArrayInputStream(baos.toByteArray())
        val ois = ObjectInputStream(bais)
        @Suppress("unchecked_cast")
        return ois.readObject() as T
    }

    /**
     * 상단 스테이터스바 색 변경
     */
    fun updateStatusBarColor(context: Activity, colorId: Int) {
        val window: Window = context.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(context, colorId)
    }

    /**
     * 이메일 주소 마스킹 처리
     * @param email
     * @return maskedEmailAddress
     */
    fun getMaskedEmail(email: String): String {
        val regex = "\\b(\\S+)+@(\\S+.\\S+)"
        val matcher: Matcher = Pattern.compile(regex).matcher(email)
        if (matcher.find()) {
            val id: String = matcher.group(1) // 마스킹 처리할 부분인 userId
            val length = id.length
            return when {
                length < 3 -> {
                    val c = CharArray(length)
                    Arrays.fill(c, '*')
                    email.replace(id, String(c))
                }
                length == 3 -> {
                    email.replace("\\b(\\S+)[^@][^@]+@(\\S+)".toRegex(), "$1**@$2")
                }
                else -> {
                    //앞에 3자리만 남기고 싶을때
                    email.replace("(^[^@]{3}|(?!^)\\G)[^@]".toRegex(), "\$1*")
                    //뒤에 3자리만 변경하고 싶을때
                    //email.replace("\\b(\\S+)[^@][^@][^@]+@(\\S+)".toRegex(), "$1***@$2")
                }
            }
        }
        return email
    }

    fun setIntToDate(year: Int, month: Int, day: Int, hour: Int, min: Int, sec: Int): Date {
        val cal = Calendar.getInstance()
        cal.set(year, month, day, hour, min, sec)
        return cal.time
    }

    //권한설정
    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 단말기 가로 해상도 구하기
     * @param context
     */
    fun getScreenWidth(context: Context): Int {
        val metrics = context.resources.displayMetrics
        return metrics.widthPixels
    }

    fun setSectoTime(sec: Int, ext: String = "{hour}:{min}:{sec}"): String {
        var value = ""
        var maxsec = sec
        var hour = 0
        var min = 0

        //hour
        if (ext.indexOf("{hour}") > -1) {
            hour = maxsec / (60 * 60)
            maxsec = maxsec % (60 * 60)

            val array = ext.split("{min}")

            if (hour > 0) {
                if (array.size > 1) {
                    value = array[0].replace("{hour}", hour.toString()) + "{min}" + array[1]
                } else {
                    value = array[0].replace("{hour}", hour.toString())
                }
            } else {
                if (array.size > 1) {
                    value = "{min}" + array[1]
                }
            }
        } else {
            value = ext
        }

        //min
        if (ext.indexOf("{min}") > -1) {
            min = maxsec / 60
            maxsec = maxsec % 60

            val array = value.split("{sec}")

            if (min > 0) {
                if (array.size > 1) {
                    value = array[0].replace("{min}", min.toString()) + "{sec}" + array[1]
                } else {
                    value = array[0].replace("{min}", min.toString())
                }
            } else if (hour > 0 && min <= 0) {
                val array = value.split("{min}")
                value = array[0]
            } else {
                if (array.size > 1) {
                    value += "{sec}" + array[1]
                }
            }
        } else if (value.isNullOrBlank()) {
            value = ext
        }


        if (ext.indexOf("{sec}") > -1) {
            val sec = maxsec

            if (sec > 0) {
                value.replace("{seq}", sec.toString())
            } else if ((hour > 0 || min > 0) && sec <= 0) {
                val array = value.split("{sec}")
                value = array[0]
            }
        }

        return value
    }

    fun convertDateToString(value: Date?): String {
        return convertDateToString(value, "yyyy-MM-dd HH:mm:ss")
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateToString(value: Date?, regx: String): String {
        var rtnvalue = ""

        try {
            if (value != null) {
                val transFormat = SimpleDateFormat(regx)
                rtnvalue = transFormat.format(value)
            }
        } catch (e: Exception) {

        }
        return rtnvalue
    }

    fun convertStringToDate(value: String): Date? {
        return convertStringToDate(value, "yyyy-MM-dd HH:mm:ss")
    }

    @SuppressLint("SimpleDateFormat")
    fun convertStringToDate(value: String, regx: String): Date? {
        var rtnvalue: Date? = null

        try {
            val transFormat = SimpleDateFormat(regx)
            rtnvalue = transFormat.parse(value)
        } catch (e: Exception) {

        }
        return rtnvalue
    }
}