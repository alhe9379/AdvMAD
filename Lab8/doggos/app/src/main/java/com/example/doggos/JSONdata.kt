package com.example.doggos

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.example.doggos.DogDataModel
import com.example.doggos.DogViewModel
import org.json.JSONException
import org.json.JSONObject

class JSONdata {

    fun loadJSON(context: Context, dogViewModel: DogViewModel){
        val url = "https://dog.ceo/api/breeds/image/random/20"
        // instantiate the Volley request queue
        val queue = Volley.newRequestQueue(context)

        // Request a string response from the provided URL.
        val request = StringRequest(Request.Method.GET, url,
            { response ->
                parseJSON(response, dogViewModel)
            },
            {
                Log.e("RESPONSE", error("request failed"))
            }
        )

        // Add the request to the RequestQueue
        queue.add(request)
    }

    fun parseJSON(response: String, dogViewModel: DogViewModel){
        val dataList = ArrayList<DogDataModel>()
        // Base url for the posters
        //val poster_base_url = "https://image.tmdb.org/t/p/w185"

        try {
            //create JSONObject
            val jsonObject = JSONObject(response)

            //create JSONArray with the value from the results key
            val imageURLS = jsonObject.getJSONArray("message")
            for(i in 0..19) {
                val newDog = DogDataModel(imageURLS[i] as String)
                dataList.add(newDog)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        dogViewModel.updateList(dataList)
    }
}