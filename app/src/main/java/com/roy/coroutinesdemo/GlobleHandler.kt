package com.roy.coroutinesdemo

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 *    desc   :
 *    author : Roy
 *    version: 1.0
 */
class GlobleHandler :CoroutineExceptionHandler{
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    //ServerLoader  全局协程异常监听
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->

        }
        log("e = ${exception.stackTraceToString()}")
    }
}