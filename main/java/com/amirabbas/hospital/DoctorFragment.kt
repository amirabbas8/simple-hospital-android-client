package com.amirabbas.hospital

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Response
import kotlinx.android.synthetic.main.fragment_doctor.*
import kotlinx.android.synthetic.main.fragment_doctor.view.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class DoctorFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_doctor, container, false)
        v.ok.setOnClickListener { add() }
        return v
    }

    fun add() {
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("hospital", Context.MODE_PRIVATE)
        val params = HashMap<String, String>()
        params["username"] = sharedPreferences?.getString("username", "").orEmpty()
        params["password"] = sharedPreferences?.getString("password", "").orEmpty()
        params["user"] = user.text.toString()
        params["prescription"] = prescription.text.toString()
        MyApplication.request(params, "add", Response.Listener { response ->
            try {
                val feedObj = JSONObject(response)
                val status = feedObj.getInt("status")
                if (status > 0) {
                    user.text.clear()
                    prescription.text.clear()
                    Toast.makeText(activity, "انجام شد", Toast.LENGTH_LONG).show()
                }
            } catch (e: JSONException) {
                Toast.makeText(activity, "خطا", Toast.LENGTH_LONG).show()
            }
        })
    }

}
