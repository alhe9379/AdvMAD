package com.example.navdrawer.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView
import com.example.navdrawer.ui.home.DogAdapter.ViewHolder
import com.example.navdrawer.R

class DogAdapter(private val dogList:ArrayList<Dog>): RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val dogTextView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemViewHolder = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = dogList[position]
        holder.dogTextView.text = dog.name
    }

    override fun getItemCount() = dogList.size

}