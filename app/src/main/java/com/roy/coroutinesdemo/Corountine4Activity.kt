package com.roy.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.launch

/**
 *  retrofit 改造 回调 suspend
 */
class Corountine4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corountine4)

        MyViewModel().login()




     /*   lifecycleScope.launch {

            whenStarted {

            }
            whenCreated {

            }

            whenResumed {

            }


        }*/
    }

    fun start1(){
        lifecycleScope.launch{  //推荐使用  具有绑定activity 生命周期

        }


    }
}