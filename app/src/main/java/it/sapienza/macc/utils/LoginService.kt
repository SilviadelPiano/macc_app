package it.sapienza.macc.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import it.sapienza.macc.BuildConfig
import org.json.JSONObject

class LoginService (c: Context?) {

    private var volleyQueue = Volley.newRequestQueue(c)
    private var TAG = this.javaClass.canonicalName

    fun sendLoginRequest(googleId: String, token: String) : Boolean{

        var url = "http://"+ BuildConfig.BE_URI +":"+ BuildConfig.BE_PORT +"/senators/auth"
        Log.i(TAG, "Address: $url")
        var req = HashMap<String,String>()
        req["googleId"]=googleId
        req["token"]=token
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(req.toMap()),
            { r ->
                val resultValue = JSONObject(r.toString()).getString("res")
                if (resultValue.equals("OK")) {
                    Log.i(TAG, "Authentication succeded.")
                } else {
                    Log.w(TAG, "Authentication with server failed.")
                }
            },
            { error: VolleyError? ->
                Log.e(TAG, "Error while sending login request to server: " + error.toString())
            })
        volleyQueue.add(stringRequest)
        return false
    }

}