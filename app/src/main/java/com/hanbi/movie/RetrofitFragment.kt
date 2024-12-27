package com.hanbi.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanbi.movie.databinding.FragmentRetrofitBinding
import com.hanbi.movie.model.MovieListModel
import com.hanbi.movie.recycler.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRetrofitBinding.inflate(inflater, container, false)
        val call : Call<MovieListModel> = MyApplication.networkService.getMovie(
            MyApplication.LANGUAGE,
           1
        )
        call?.enqueue(object : Callback<MovieListModel>{
            override fun onResponse(call: Call<MovieListModel>, response: Response<MovieListModel>) {
                if(response.isSuccessful()){
                    binding.retrofitRecyclerView.layoutManager =
                        LinearLayoutManager(activity)
                    binding.retrofitRecyclerView.adapter =
                        MyAdapter(activity as Context, response.body()?.results)
                }
            }

            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {


            }
        })
        return binding.root
    }
}