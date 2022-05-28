package com.example.wallpaperapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WallpaperAdapter(private  val listener:WallpaperItemClicked): RecyclerView.Adapter<WallpaperViewHolder>() {

    private val items: ArrayList<wallpaper> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallpaper,parent,false)
        val viewHolder=WallpaperViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val currentItem= items[position]
        holder.views.text= currentItem.views.toString()
        holder.downloads.text= currentItem.downloads.toString()
        holder.likes.text= currentItem.likes.toString()
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateWallpapers(updatedWallpapers: ArrayList<wallpaper>){
        items.clear()
        items.addAll(updatedWallpapers)
        notifyDataSetChanged()
    }

}
class WallpaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val views:TextView=itemView.findViewById(R.id.views)
    val image:ImageView=itemView.findViewById(R.id.image)
    val downloads:TextView=itemView.findViewById(R.id.downloads)
    val likes:TextView=itemView.findViewById(R.id.Likes)
}
interface WallpaperItemClicked{
    fun onItemClicked(item: wallpaper)
}