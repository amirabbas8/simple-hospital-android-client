package com.amirabbas.hospital

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences: SharedPreferences = getSharedPreferences("hospital", Context.MODE_PRIVATE)
        val isLogged = sharedPreferences.getBoolean("isLogged", false)
        if (isLogged) {
            logout.setOnClickListener { logout(sharedPreferences) }
            when (sharedPreferences.getInt("user_type", -1)) {
                1, 3 -> {//user ,pharmacy
                    supportFragmentManager.beginTransaction().add(fragment.id, PrescriptionsFragment(), "").commit()
                }
                2 -> {//doctor
                    supportFragmentManager.beginTransaction().add(fragment.id, DoctorFragment(), "").commit()
                }
                else -> {
                    logout(sharedPreferences)
                }
            }
        } else {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    fun logout(sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLogged", false)
        editor.putString("username", "")
        editor.putString("password", "")
        editor.putInt("user_type", -1)
        editor.apply()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}
