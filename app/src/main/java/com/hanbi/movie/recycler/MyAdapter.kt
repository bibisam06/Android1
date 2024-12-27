package com.hanbi.movie.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.hanbi.movie.MyApplication.Companion.API_KEY
import com.hanbi.movie.databinding.ItemMainBinding
import com.hanbi.movie.model.ItemModel
import okhttp3.OkHttpClient

class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context : Context, val datas : MutableList<ItemModel>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position : Int){
            val binding=(holder as MyViewHolder).binding
            val model = datas!![position]
            binding.itemTitle.text = model.title
            binding.itemAdult.text = model.adult
            binding.itemReleaseDate.text = "released At ${model.release_date}"
            binding.itemVote.text = "rate : ${model.vote_average}"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer YOUR_API_KEY")  // TMDb API 키
                    .build()
                chain.proceed(request)
            }
            .build()

        val glideUrl = GlideUrl("http://image.tmdb.org/t/p/w500" + model.poster_path, LazyHeaders.Builder()
            .addHeader("Authorization", "Bearer ${API_KEY}") // TMDb API 키
            .build())

        Glide.with(context)
            .load(glideUrl)
            .into(binding.itemPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }
}