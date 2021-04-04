package com.helrin.utillib

import java.util.regex.Pattern

/**
 * 정규식 체크
 */

object RegularValid {
    /**
     * 이메일 체크 정규식
     */
    fun isEmailValid(email: String): Boolean {
        val emailValidation =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(emailValidation, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    /**
     * 비밀번호 체크 정규식
     */
    fun isPasswordValid(password: String): Boolean {
        val passwordValid = "^.*(?=^.{8,15}$)(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%]).*$"
        val pattern = Pattern.compile(passwordValid, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}