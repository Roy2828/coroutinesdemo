package com.roy.coroutinesdemo

import kotlinx.coroutines.*

/**
 *    desc   : 协程的上下文
 *    author : Roy
 *    version: 1.0
 */


fun main(){
    val t = Corountine2()
    t.start()
    //t.coroutineContextTest()
    //t.coroutineContextTest1()
    Thread.sleep(1000)
}
class Corountine2 {

    fun start(){
        log("start ....")
        val launchJob = GlobalScope.launch(context = Dispatchers.Main,start = CoroutineStart.LAZY) {
            Thread.sleep(10000)
            log("launch 启动一个协程")
        }

        launchJob.start()
        log("launchJob= $launchJob")
    }

    fun coroutineContextTest(){
        val coroutineContext = Job() + Dispatchers.Default + CoroutineName("myContext")
        log("$coroutineContext,${coroutineContext[CoroutineName]}")
        val newCoroutineContext = coroutineContext.minusKey(CoroutineName)
        log("$newCoroutineContext")
    }

    fun coroutineContextTest1(){
        val coroutineContext = Dispatchers.Default + CoroutineName("myContext")
        log("$coroutineContext,${coroutineContext[CoroutineName]}")
        val newCoroutineContext = coroutineContext + Dispatchers.IO //所以加号右侧的元素会覆盖加号左侧的元素，进而组成新创建的 CoroutineContext
        log("$newCoroutineContext")
    }


}