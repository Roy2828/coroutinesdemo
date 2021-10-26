package com.roy.coroutinesdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 *    desc   :
 *    author : Roy
 *    version: 1.0
 */

interface FackApi {
    @POST("/form")
    @FormUrlEncoded
    suspend fun login(@Field("name") name: String): List<Any>
}
class MyViewModel : ViewModel() {

    fun login() {
        val retrofit = Retrofit.Builder()
            .baseUrl(HttpUrl.get("https://www.baidu.com"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val loginI = retrofit.create(FackApi::class.java)

        viewModelScope.launch {  // viewmode的协程

            val result = loginI.login("dddd")
        }
    }

    fun start1(){
        viewModelScope.launch{  //推荐使用  具有绑定fragment // 生命周期

        }


    }


}