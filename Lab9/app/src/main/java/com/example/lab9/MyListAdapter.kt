package com.example.list

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9.R
import com.example.lab9.data.DataStoreViewModel
import com.example.lab9.model.DataStoreListItem
import com.example.lab9.model.FirebaseViewModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.toObject

//class MyListAdapter(private val itemViewModel: DataStoreViewModel): RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
//    private var myItemList = itemViewModel.itemList.value
//
//    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
//        val itemTextView: TextView = view.findViewById(R.id.textView)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val itemViewHolder = layoutInflater.inflate(R.layout.list_item, parent, false)
//        return ViewHolder(itemViewHolder)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = myItemList?.get(position)
//        holder.itemTextView.text = item?.question + "   " + item?.answer  ?: ""
//
//        holder.itemView.setOnCreateContextMenuListener(){menu, view, menuInfo ->
//            menu.setHeaderTitle(R.string.delete)
//            menu.add(R.string.yes).setOnMenuItemClickListener{
//                itemViewModel.delete(item!!)
//                Snackbar.make(view, R.string.deleteItem, Snackbar.LENGTH_LONG)
//                    .setAction(R.string.deleteItem, null).show()
//                true
//            }
//            menu.add(R.string.no)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        if(myItemList != null){
//            return myItemList!!.size
//        } else return 0
//    }
//
//    fun update(){
//        myItemList=itemViewModel.itemList.value
//        notifyDataSetChanged()
//    }
//}

class MyListAdapter(options: FirestoreRecyclerOptions<DataStoreListItem>, private val viewModel: FirebaseViewModel) : FirestoreRecyclerAdapter<DataStoreListItem, MyListAdapter.ViewHolder>(options)  {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recipeViewHolder = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(recipeViewHolder)
    }

    override fun onBindViewHolder(holder: MyListAdapter.ViewHolder, position: Int, model: DataStoreListItem) {
        //use model that is passed in and assign the properties defined in the ViewHolder class
        holder.nameTextView.text = model.question
        //context menu
        holder.itemView.setOnCreateContextMenuListener(){menu, view, menuInfo ->
            //set the menu title
            menu.setHeaderTitle(R.string.delete)

            //add the choices to the menu
            menu.add(R.string.yes).setOnMenuItemClickListener {
                //get recipe that was clicked
                //snapshots gets the array that the adapter is populated with
                //getSnapshot returns the snapshot at the position
                val id = snapshots.getSnapshot(position).id
                //delete item
                viewModel.delete(id)
                Snackbar.make(view, R.string.delete, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action, null).show()
                true
            }
            menu.add(R.string.no)
        }
    }
}