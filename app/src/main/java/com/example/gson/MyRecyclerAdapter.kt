package com.example.gson

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyRecyclerAdapter (private val context: Context, private val links : Array<String>) : RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>(){
    class  ViewHolder (itemView : View):RecyclerView.ViewHolder (itemView){
        val pic :ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.r_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return links.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = links[position]
        Glide.with(context).load(data).into(holder.pic)

    }
}
