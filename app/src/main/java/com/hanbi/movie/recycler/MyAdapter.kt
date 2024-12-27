package com.hanbi.movie.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanbi.movie.databinding.ItemMainBinding
import com.hanbi.movie.model.ItemModel

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
            Glide.with(context)
                .load(model.poster_path)
                .into(binding.itemPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }
}