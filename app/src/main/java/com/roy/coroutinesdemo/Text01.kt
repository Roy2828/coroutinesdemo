package com.roy.coroutinesdemo

import kotlinx.coroutines.*

/**
 *    desc   :
 *    author : Roy
 *    version: 1.0
 */
class Text01 {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)



    fun suspendTest1(){
        scope.launch {
            log("start ....")
            val result = paserJson2()
            log(result)
        }
    }
    //方式二
    suspend fun  paserJson2(): String {
       // 将回调函数转换为协程
        return suspendCancellableCoroutine { cont ->
            Thread{
                log("开始执行任务。。。")
                Thread.sleep(2000)
                log("执行完了。。。 ")
                cont.resumeWith(Result.success("suspend xxxxxxx"))
                log("结果返回了")
            }.start()
        }
    }
}