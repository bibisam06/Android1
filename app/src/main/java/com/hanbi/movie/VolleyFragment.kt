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
        val binding = FragmentVolleyBinding.inflate(inflater, container, false)

        // 데이터 로드 및 UI 초기화
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
                        val article = jsonArray.getJSONObject(i)
                        val item = ItemModel().apply {
                            adult  = article.optString("adult", "Unknown")
                            title = article.optString("title", "No Title")
                            release_date = article.optString("release_date", "No Date")
                            poster_path = article.optString("poster_path", "null")
                            vote_average = article.optString("vote_average", "0")
                            mutableList.add(this)
                        }

                    }

                    // RecyclerView에 데이터 연결
                    binding.volleyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.volleyRecyclerView.adapter = MyAdapter(activity as Context, mutableList)
                } catch (e: Exception) {
                    Log.e("VolleyError", "Error parsing JSON: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                println("error,,,,,,,,,$error") }) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = mutableMapOf<String, String>(
                    "User-agent" to MyApplication.USER_AGENT
                )
                return map
            }
        }

        queue.add(jsonRequest)
    }
}