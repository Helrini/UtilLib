package com.helrin.utillib

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * Rest api 통신 -> Requset Body 생성
 */
object JsonRequest {
    fun createJsonRequestBody(params: Map<String, Any>) =
        JSONObject(params).toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

    fun createJsonRequestBody(vararg params: Pair<String, Any>) =
        JSONObject(mapOf(*params)).toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

    fun createJsonRequestBody(params: String) =
        params.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

}