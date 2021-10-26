package com.roy.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.roy.coroutinesdemo.databinding.ActivityCorountine3Binding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 协程的推荐用法
 * @property scope CoroutineScope
 * @property mainScope CoroutineScope
 * @property scopeIO CoroutineScope
 * @property binding [@org.jetbrains.annotations.NotNull] ActivityCorountine3Binding
 * @property job CompletableJob
 * @property coroutineContext CoroutineContext
 */
class Corountine3Activity : AppCompatActivity(), CoroutineScope {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val mainScope = MainScope()
    private val scopeIO = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val binding by lazy {
        ActivityCorountine3Binding.inflate(layoutInflater)

    }

    private val job by lazy {
        SupervisorJob()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        GlobalScope.launch {  //不推荐使用不会和activity生命周期同步
            log("Global launch...")
        }

        setOnclicks()
    }



    fun setOnclicks() {
        binding.apply {
            btnTest1.setOnClickListener {
                log("btnTest1 onclick ...")
                scope.launch {
                    log("top level launch")
                    scopeIO.launch {
                        log("IO level launch")
                    }
                    scope.launch(Dispatchers.IO) {
                        log("1 level launch")
                        launch(Dispatchers.Unconfined) {
                            log("2 level launch")
                            launch(Dispatchers.Main) {
                                log("3 level launch")
                            }
                        }
                        log("end ..... 1 level launch")
                    }
                    log("end ..... top level launch")
                }

            }

            btnTest2.setOnClickListener {
                log("btnTest2 onclick ...")
                scope.launch {
                    log("top level launch")
                    //withContext {}不会创建新的协程，在指定协程上运行挂起代码块，并挂起该协程直至代码块运行完成
                    withContext(Dispatchers.IO){
                        log("切换线程到 IO")
                    }
                    log("切换到IO后。。。。")

                    withContext(Dispatchers.Default){
                        log("切换线程到 Default")
                    }
                    log("切换到IDefault后。。。。")
                    log("end ..... top level launch")

                }
            }




        }
    }

    suspend fun doSomeThing(){
        delay(1000)
        log("doSomeThing")
    }

    suspend fun doOtherThing(){
        delay(2000)
        log("doOtherThing")
    }

    fun test1(){
        scope.launch(Dispatchers.Main) {
            log("start ....")
            doSomeThing()
            doOtherThing()
            log("end .....")
        }
    }

    fun test2(){   //以下是按照顺序执行的    withContext 切换线程  io线程 再次切换io线程 是在同一条线程上不会创建新的线程
        scope.launch(Dispatchers.Main) {
            log("start ....")
            withContext(Dispatchers.IO){
                doSomeThing()
            }
            withContext(Dispatchers.IO){
                doOtherThing()
            }
            log("end .....")
        }
    }

    fun test3(){  //按照顺序执行
        scope.launch(Dispatchers.Main) {
            log("start ....")
            withContext(Dispatchers.IO){//suspend
                doSomeThing()
            }
            doOtherThing()
            log("end .....")
        }
    }

    fun test4(){ //不会按照顺序执行
        scope.launch(Dispatchers.Main) {
            log("start ....")
            launch {//新创建了协程   不是同一条协程  不会阻塞下面的代码
                doSomeThing()
            }
            launch {
                doOtherThing()
            }
            log("end .....")
        }
    }




    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        scopeIO.cancel()
        job.cancel()
    }
}