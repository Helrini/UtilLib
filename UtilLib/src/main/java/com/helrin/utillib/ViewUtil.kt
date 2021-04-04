package com.helrin.utillib

import android.app.Activity
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Keep

object ViewUtil {
    fun changeSelecties(status: Boolean, vararg views: View) {
        for (view in views) view.isSelected = status
    }

    fun changeGone(vararg views: View) {
        for (view in views) view.visibility = View.GONE
    }

    fun changeViewGone(vararg ids: View) {
        for (view in ids) view.visibility = View.GONE
    }

    fun changeVisible(vararg views: View) {
        for (view in views) view.visibility = View.VISIBLE
    }

    fun changeReverseSelect(v: View?) {
        if (v != null) {
            v.isSelected = !v.isSelected
        }
    }

    fun changeReverseShow(v: View?) {
        if (v != null) {
            if (v.isShown) v.visibility = View.GONE else v.visibility = View.VISIBLE
        }
    }

    fun changeEnable(layout: LinearLayout, value: Boolean) {
        for (i in 0 until layout.childCount) {
            val v = layout.getChildAt(i)
            v.isEnabled = value
        }
    }
}