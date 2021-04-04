package com.helrin.utillib

import android.util.Log
import androidx.annotation.Keep

object L {
    private var DEBUG_FLAG    = true
    private var ERROR_FLAG    = true
    private var INFO_FLAG     = true
    private var WARNING_FLAG  = true
    private var VERBOS_FLAG   = true
    fun d(tag: String?, message: String) {
        if (DEBUG_FLAG) {
            Log.d(tag, buildLogMsg(message))
        }
    }

    fun e(tag: String?, message: String) {
        if (ERROR_FLAG) {
            Log.e(tag, buildLogMsg(message))
        }
    }

    fun i(tag: String?, message: String) {
        if (INFO_FLAG) {
            Log.i(tag, buildLogMsg(message))
        }
    }

    fun w(tag: String?, message: String) {
        if (WARNING_FLAG) {
            Log.w(tag, buildLogMsg(message))
        }
    }

    fun v(tag: String?, message: String) {
        if (VERBOS_FLAG) {
            Log.v(tag, buildLogMsg(message))
        }
    }

    fun setD(boolean: Boolean){
        DEBUG_FLAG = boolean
    }

    fun setE(boolean: Boolean){
        ERROR_FLAG = boolean
    }

    fun setI(boolean: Boolean){
        INFO_FLAG = boolean
    }

    fun setW(boolean: Boolean){
        WARNING_FLAG = boolean
    }

    fun setV(boolean: Boolean){
        VERBOS_FLAG = boolean
    }

    private fun buildLogMsg(message: String): String {
        val ste = Thread.currentThread().stackTrace[4]

        return StringBuilder().apply {
            append("[")
            append(ste.fileName)
            append(" > ")
            append(ste.methodName)
            append(" > #")
            append(ste.lineNumber)
            append("] ")
            append(message)
        }.toString()
    }
}