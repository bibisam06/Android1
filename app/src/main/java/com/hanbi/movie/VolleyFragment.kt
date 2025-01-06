package com.hanbi.movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hanbi.movie.databinding.FragmentVolleyBinding
import com.hanbi.movie.model.ItemModel
import com.hanbi.movie.recycler.MyAdapter
import org.json.JSONObject

class VolleyFragment : Fragment(){
    private var _binding: FragmentVolleyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVolleyBinding.inflate(inflater, container, false)
        loadData()
        return binding.root
    }


    private fun loadData() {
        val url =
        MyApplication.BASE_URL + "/movie/now_playing?language=" +
                "${MyApplication.LANGUAGE}&page=1"

        val queue = Volley.newRequestQueue(requireContext())
        val jsonRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                try {
                    val jsonArray = response.getJSONArray("results")
                    val mutableList = mutableListOf<ItemModel>()
                    for (i in 0 until jsonArray.length()) {
                        ItemModel().run {
                            val article = jsonArray.getJSONObject(i)

                            val item = ItemModel().apply {
                                adult  = article.optString("adult", "Unknown")
                                if(adult == "false"){
                                    adult = "청소년 관람 가능"
                                }else{
                                    adult = "청소년 관람 불가"
                                }
                                title = article.optString("title", "No Title")
                                release_date = article.optString("release_date", "No Date")
                                poster_path = article.optString("poster_path", "No Poster")
                                vote_average = article.optString("vote_average", "nothing....")
                                mutableList.add(this)
                            }

                        }

                    }


                    binding.volleyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.volleyRecyclerView.adapter = MyAdapter(activity as Context, mutableList)
                } catch (e: Exception) {
                    Log.e("VolleyError", "Error parsing JSON: ${e.message ?: e.toString()}")
                }
            },
            Response.ErrorListener { error ->
                println("error,,,,,,,,,$error") }) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = mutableMapOf<String, String>(
                    "accept" to "application/json",
                    "Authorization" to "Bearer ${MyApplication.API_KEY}"
                )
                return map
            }
        }

        queue.add(jsonRequest)
    }
}