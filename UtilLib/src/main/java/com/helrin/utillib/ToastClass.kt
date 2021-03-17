package com.helrin.utillib

import android.content.Context
import android.widget.Toast

class ToastClass {
    companion object{
        fun showToast(context: Context, msg: String) { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
    }
}