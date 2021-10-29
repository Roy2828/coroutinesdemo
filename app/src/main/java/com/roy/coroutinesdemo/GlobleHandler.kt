package com.roy.coroutinesdemo

import kotlinx.coroutines.CoroutineExceptionHandler
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 *    desc   :
 *    author : Roy
 *    version: 1.0
 */
class GlobleHandler :CoroutineExceptionHandler{
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    //ServiceLoader  全局协程异常监听
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->

        }
        log("e = ${exception.stackTraceToString()}")
    }


/*
  加载上面的类
    private val handlers: List<CoroutineExceptionHandler> = ServiceLoader.load(
        CoroutineExceptionHandler::class.java,
        CoroutineExceptionHandler::class.java.classLoader
    ).iterator().asSequence().toList()*/
}