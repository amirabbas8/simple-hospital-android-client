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
import kotlinx.android.synthetic.main.fragment_prescriptions.*
import kotlinx.android.synthetic.main.fragment_prescriptions.view.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class PrescriptionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_prescriptions, container, false)
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("hospital", Context.MODE_PRIVATE)
        v.ok.setOnClickListener { get(sharedPreferences, v.user.text.toString()) }
        if (sharedPreferences?.getInt("user_type", -1) != 3) {
            v.user.setText(sharedPreferences?.getString("username", ""))
            v.user.isEnabled = false
        } else {
        }
        return v
    }

    fun get(sharedPreferences: SharedPreferences?, user: String) {
        val params = HashMap<String, String>()
        params["username"] = sharedPreferences?.getString("username", "").orEmpty()
        params["password"] = sharedPreferences?.getString("password", "").orEmpty()
        params["user"] = user
        MyApplication.request(params, "get", Response.Listener { response ->
            try {
                val feedObj = JSONObject(response)
                val status = feedObj.getInt("status")
                if (status > 0) {
                    val feedArray = feedObj.getJSONArray("prescriptions")
                    val sb = StringBuilder()
                    for (i in 0 until feedArray.length()) {
                        val p = feedArray.get(i)
                        sb.append("نسخه$i:\n").append(prescriptions.text).append(p.toString()).append("\n****\n")
                    }
                    prescriptions.text = sb.toString()
                }
            } catch (e: JSONException) {
                Toast.makeText(activity, "خطا", Toast.LENGTH_LONG).show()
            }
        })
    }

}
