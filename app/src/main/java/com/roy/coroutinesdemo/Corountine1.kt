package com.roy.coroutinesdemo

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *    desc   : 基础简介
 *    author : Roy
 *    version: 1.0
 */

fun test1(){
    var coroutines1 = Coroutines1()
    coroutines1.start5()
    //coroutines1.start1()
    //coroutines1.start2()
    //coroutines1.start3()

}

fun main(){
    test1()
}
class Coroutines1 {
    //1. 任何创建一个协程
    fun start(){  //当前方法如果运行在main函数中  下面注释才是正确的
         log("start")
        runBlocking {    //这段代码 在实际开发中是不会用到的 一般作为测试用
            delay(2000)
            log("runBlocking 启动一个协程")
        } //下面的代码不会再次执行了  因为上面睡眠两秒后 main函数已经执行完了  并且内部源码coroutine.joinBlocking() 在等待睡眠执行完毕
        GlobalScope.launch { // 运行在main函数中 这段代码 连log都不会执行就会被退出了
            delay(2000)
            log("launch 启动一个协程")
        }
        log("start1")
        GlobalScope.async {   //程序代码运行到这里 还没跳到协程 main函数就结束了
            //delay(2000)
            log("async 启动一个协程")  //这段代码也不会执行到
        }


        GlobalScope.launch {
            withContext(Dispatchers.IO){

            }
            withContext(Dispatchers.Main){

            }
        }
    }

    fun start1(){
        val runBlockingJob = runBlocking {
            log("runBlocking 启动一个协程")
        }
        log("runBlockingJob= $runBlockingJob")

        val launchJob = GlobalScope.launch {
            log("launch 启动一个协程")
        }
        log("launchJob= $launchJob")

        val asyncJob = GlobalScope.async {
            log("async 启动一个协程")
        }
        log("asyncJob= $asyncJob")
    }

    fun start2() = runBlocking {
        val runBlockingJob = runBlocking {
            log("runBlocking 启动一个协程")
        }
        log("runBlockingJob= $runBlockingJob")

        val launchJob = GlobalScope.launch {
            log("launch 启动一个协程")
        }
        log("launchJob= $launchJob")
        val asyncJob = GlobalScope.async {
            log("async 启动一个协程")
            "我是async的返回值"
        }
        //await()是一个suspend 函数，suspend函数必须在suspend函数里面调用
        log("asyncJob.await= ${asyncJob.await()}")
        log("asyncJob= $asyncJob")
        "我是runBlocking的返回值"
    }

    fun start3(){

        val scope = CoroutineScope(EmptyCoroutineContext)//CoroutineScope函数
        scope.launch {
            log("scope launch")
        }
        scope.async {
            log("scope async")
        }

        class MyCoroutineScope : CoroutineScope {
            override val coroutineContext: CoroutineContext
                get() = EmptyCoroutineContext

        }
        val myCustomScope = MyCoroutineScope()
        myCustomScope.launch {
            log("myCustomScope launch")
        }
        myCustomScope.async {
            log("myCustomScope async")
        }

    }

    fun start4(){   //顺序执行
        GlobalScope.launch(Dispatchers.Default) {
            // delay(2000)
            log("launch 启动一个协程")
            withContext(Dispatchers.IO){
                delay(2000)
                log("launch 启动一个协程1IO")
            }

            withContext(Dispatchers.Main){
                log("launch 启动一个协程2Main")
            }

            launch {
                log("launch 启动一个协程3IO")
            }
        }


    }

    fun start5(){
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            log("launch 启动一个协程")
        }

GlobalScope.async {  }
    }

}