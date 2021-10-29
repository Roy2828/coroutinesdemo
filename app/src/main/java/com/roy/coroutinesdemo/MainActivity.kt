package com.roy.coroutinesdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_test.setOnClickListener{
            var coroutines1 = Coroutines1()
            coroutines1.start5()
        }

        tv_01.setOnClickListener{
            startActivity(Intent(this@MainActivity,Corountine3Activity::class.java))
        }

        tv_02.setOnClickListener {
            startActivity(Intent(this@MainActivity,Corountine4Activity::class.java))
        }

        tv_03.setOnClickListener {
            startActivity(Intent(this@MainActivity,Corountine5Activity::class.java))
        }

        tv_06.setOnClickListener {
            startActivity(Intent(this@MainActivity,Corountine6Activity::class.java))
        }

        tv_04.setOnClickListener{
            Toast.makeText(this,"暂无功能",0).show()
        }
        tv_05.setOnClickListener{
          //  Toast.makeText(this,"暂无功能",0).show()
        Text01().suspendTest1()
        }

    }


}