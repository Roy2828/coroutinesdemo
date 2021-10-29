package com.roy.coroutinesdemo

import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

/**
 *    desc   :  协程作用域
 *    author : Roy
 *    version: 1.0
 */
fun main() {
    val p = Corountine6()
    p.test0()
}

class Corountine6 {

    fun test0() = runBlocking {
        log("0")

        val handler = CoroutineExceptionHandler { _, e ->
            log("e=${e.stackTraceToString()}")
        }
        try {
            val job = GlobalScope.launch(handler) {
                log("1")
                readFile()
                log("11")
            }
            job.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        log("2")
    }

    fun test01() {//主从和协同
        log("0")

       //  var handler = GlobleHandler() ; //自定义协程全局异常捕获 这个类加载 CoroutineExceptionHandlerImplKt.class
        val handler = CoroutineExceptionHandler { _, e ->
            log("e=${e.stackTraceToString()}")
        }
        val job1 = SupervisorJob()
        val scope = CoroutineScope(job1) //这样使用还是协同作用域
        scope.launch {
            log("1")
            supervisorScope { //主从作用域   下级协程抛异常不会影响兄弟协程

                runBlocking {
                    delay(5000)
                    log("hahahhah")
                }  //会阻塞  直到5秒后 下面代码才会执行

                val childjob1 =launch(Dispatchers.IO) {
                    for (i in 0..10) {
                        delay(500)
                        log("i = $i")
                    }
                }
                val childjob2 =launch(Dispatchers.IO) {
                    log("readfile before")
//                readFile()
                    throw NullPointerException()
                    log("readfile end ")
                }
                childjob2.join()
                childjob1.join()
                log("11")
            }

        }
        log("2")
    }

    fun test2() {  //主从作用域
        GlobalScope.launch {
            val job = SupervisorJob()

            val scope = MainScope()
            scope.launch {// 子作用域

            }
            with(CoroutineScope(coroutineContext + job)){//主从作用域   with表示在当前作用域
                val childjob1 =launch(Dispatchers.IO) {
                    for (i in 0..10) {
                        delay(500)
                        log("i = $i")
                    }
                }
                val childjob2 =launch(Dispatchers.IO) {

                    launch {
                        throw NullPointerException()
                    }

                    launch {
                        log("readfile before")
                    }

//                readFile()

                    log("readfile end ")
                }
                childjob2.join()
                childjob1.join()
                log("11")
            }
        }

    }

  fun  test3(){ //协同作用域
      //  var handler = GlobleHandler() ;
      val coroutineContext = Job() + Dispatchers.Default + CoroutineName("myContext")

      CoroutineScope(coroutineContext).launch {//外部是协同
          supervisorScope {
              //加在这里就是主从
          }
         //CoroutineExceptionHandler 只有设置在根作用域有效
              val job =  launch() {
                  log("1")
                  readFile()   //异常已经被GlobleHandler全局捕获了
                  log("11")
                  GlobalScope.launch() { //这里如果抛异常 其他所有协程都会取消掉
                      delay(500)
                      log("3")
                      readFile() //异常已经被GlobleHandler全局捕获了
                      log("33")
                  }

              }


              launch() { //这里如果抛异常 其他所有协程都会取消掉
                  delay(500)
                  log("2")
                  readFile()
                  log("22")
              }

          }


  }

    fun test1() = runBlocking {
        log("0")
        val handler = CoroutineExceptionHandler { _, e ->
            log("e=${e.stackTraceToString()}")
        }
        val deferred = GlobalScope.async(handler) {
            log("1")
            readFile()
            log("11")
        }
        deferred.join()

        try {
            deferred.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        log("2")
    }

    fun readFile() {
//        try{}catch (e:Exception){}
        val fis = FileInputStream(File(""))  //会抛异常的
        fis.read()
    }
}