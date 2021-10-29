package com.roy.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_corountine6.*

/**
 * 协程作用域
 */
class Corountine6Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corountine6)

        tv_one.setOnClickListener{
            val p = Corountine6()
            //  p.test01()
              p.test2()
           // p.test3()
            //p.test3()
        }
    }
}