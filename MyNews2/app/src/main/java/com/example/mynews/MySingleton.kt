package com.example.mynews

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

data class MySingleton constructor(val context:Context)
{
    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: MySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MySingleton(context).also {
                    INSTANCE = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy{
        Volley.newRequestQueue(context.applicationContext)
    }
     fun <T> addRequestQueue(req: Request<T>){
       requestQueue.add(req)
    }



}
