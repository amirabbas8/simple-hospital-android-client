package com.amirabbas.hospital

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*


class MyApplication : Application() {
    private var mRequestQueue: RequestQueue? = null

    val requestQueue: RequestQueue
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            }

            return mRequestQueue!!
        }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue.add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        requestQueue.add(req)
    }

    fun cancelPendingRequests(tag: Any) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }


    companion object {
        val TAG = MyApplication::class.java.simpleName
        @get:Synchronized
        var instance: MyApplication? = null
            private set

        fun request(params: HashMap<String, String>, req: String, listener: Response.Listener<String>) {
            val strReq = object : StringRequest(
                Request.Method.POST,
                "http://10.0.3.2:8000/$req", listener, Response.ErrorListener {}) {

                override fun getParams(): Map<String, String> {
                    return params
                }
            }
            MyApplication.instance?.addToRequestQueue(strReq)
        }

    }

}