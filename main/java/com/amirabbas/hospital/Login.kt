package com.amirabbas.hospital

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Response
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "ورود"
        ok.setOnClickListener {
            login()
        }
    }

    fun login() {

        val params = HashMap<String, String>()
        params["username"] = username.text.toString()
        params["password"] = password.text.toString()
        MyApplication.request(params, "auth", Response.Listener { response ->
            try {
                val feedObj = JSONObject(response)
                val status = feedObj.getInt("status")
                if (status > 0) {
                    val sharedPreferences: SharedPreferences =
                        getSharedPreferences("hospital", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLogged", true)
                    editor.putString("username", username.text.toString())
                    editor.putString("password", password.text.toString())
                    editor.putInt("user_type", status)
                    editor.apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            } catch (e: JSONException) {
                Toast.makeText(this, "خطا", Toast.LENGTH_LONG).show()
            }
        })
    }


}
