package com.example.doggos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyListAdapter(private val dogViewModel: DogViewModel): RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    private var myDogList = dogViewModel.dogList.value

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemViewHolder = layoutInflater.inflate(R.layout.card_list_item, parent, false)
        return ViewHolder(itemViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = myDogList?.get(position)

        Picasso.get().load(dog?.imageURL ?: "")
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView);
    }

    override fun getItemCount(): Int {
        if(myDogList != null) {
            return myDogList!!.size
        } else return 0
    }

    fun update(){
        myDogList = dogViewModel.dogList.value
        notifyDataSetChanged()
    }

}