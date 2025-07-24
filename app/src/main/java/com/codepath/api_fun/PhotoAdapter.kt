package com.codepath.api_fun

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoAdapter(private val photos: List<MarsPhoto>) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val roverNameText: TextView = itemView.findViewById(R.id.roverNameText)
        val cameraNameText: TextView = itemView.findViewById(R.id.cameraNameText)
        val solDateText: TextView = itemView.findViewById(R.id.solDateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]

        Glide.with(holder.itemView.context)
            .load(photo.imgSrc)
            .into(holder.imageView)

        holder.roverNameText.text = "Rover: ${photo.roverName}"
        holder.cameraNameText.text = "Camera: ${photo.cameraFullName}"
        holder.solDateText.text = "Sol: ${photo.sol}"
    }

    override fun getItemCount(): Int = photos.size
}