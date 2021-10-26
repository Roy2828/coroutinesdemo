package com.roy.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_corountine5.*
import kotlinx.coroutines.*

/**
 * suspend 关键字
 * @property scope CoroutineScope
 * @property scopeIO CoroutineScope
 */
class Corountine5Activity : AppCompatActivity() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val scopeIO = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corountine5)
        tv_one.setOnClickListener{
            log(" onclick ...")
//                test4()
            normalTest()
            suspendTest1()
        }

    }



    fun paserJson(callback: (String) -> Unit){
        Thread{
            log("开始干活了")
            Thread.sleep(5000)
            callback("result: xxxxx ")
            log("活干完了")
        }.start()
    }

    fun normalTest(){
        log("mormalTest")
        paserJson {
                content ->
            log(content)
        }
    }

    fun paserJson1(): String{
        Thread.sleep(2000)
        return "xxxxx"
    }

    //方式一
    fun suspendTest(){
        scope.launch {
            val result = scopeIO.async { paserJson1() }.await()
            log(result)
        }
    }

    //方式二
    suspend fun  paserJson2(): String {
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

    fun suspendTest1(){
        scope.launch {
            log("start ....")
            val result = paserJson2()
            log(result)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        scopeIO.cancel()
    }

}