package com.helrin.utillib

import android.content.Context
import android.widget.Toast

/**
 * Toast 생성
 */
object ToastUtil {
    fun shortToast(context: Context, msg: String) { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
    fun longToast(context: Context, msg: String) { Toast.makeText(context, msg, Toast.LENGTH_LONG).show() }
}