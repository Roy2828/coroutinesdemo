package com.roy.coroutinesdemo

import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat

/**
 *    desc   :
 *    author : Roy
 *    version: 1.0
 */

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    inline fun Long.date(): String = sdf.format(this)
    inline fun isAndroid() = try {
        Build.VERSION.SDK_INT != 0
    } catch (e: ClassNotFoundException) {
        false
    }

    fun log(value: Any?) = log(value.toString())
    fun log(msg: String) = if (isAndroid()) Log.i(
        "Roy",
        "[${System.currentTimeMillis().date()}]-[${Thread.currentThread().name}] $msg"
    ) else println("[${System.currentTimeMillis().date()}]-[${Thread.currentThread().name}] $msg")

