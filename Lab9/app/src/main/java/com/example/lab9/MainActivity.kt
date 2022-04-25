package com.example.lab9

import android.content.ClipData
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9.data.DataStoreRepo
import com.example.lab9.data.DataStoreViewModel
import com.example.lab9.data.DataStoreViewModelFactory
import com.example.lab9.model.DataStoreListItem
import com.example.lab9.model.FirebaseViewModel
import com.example.lab9.util.Database
import com.example.list.MyListAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

private val Context.dataStore by preferencesDataStore(name = "dataStoreData")

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: DataStoreViewModel
    private lateinit var question: TextView
    private lateinit var answer: TextView
    private val db = Database()
    private lateinit var dataStoreList: ArrayList<DataStoreListItem>;

    private val firebaseViewModel: FirebaseViewModel by viewModels()
    private var firebaseAdapter: MyListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this,
            DataStoreViewModelFactory(DataStoreRepo(dataStore)))[DataStoreViewModel::class.java]

        question = findViewById(R.id.editTextQuestion)
        answer = findViewById(R.id.editTextAnswer)

        findViewById<MaterialButton>(R.id.saveToDataStoreButton).setOnClickListener{ view ->
            val myQuestion = question.text.toString()
            val myAnswer = answer.text.toString()
            viewModel.saveDataStoreData(myQuestion, myAnswer)
            //Snackbar.make(view, R.string.saved, Snackbar.LENGTH_LONG).show()
            viewModel.add(DataStoreListItem(myQuestion, myAnswer))
            //Log.i("Contents", viewModel.getList().toString())
            firebaseViewModel.addToDb(DataStoreListItem(myQuestion, myAnswer))
        }

        viewModel.dataStoreData.observe(this, Observer { dataStoreData ->
            Log.i("Contents", dataStoreData.toString())
            question.text = dataStoreData.question
            answer.text = dataStoreData.answer
        })

        /////////////////////////////

        val recyclerView: RecyclerView = findViewById(R.id.dataStoreRecyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val options = firebaseViewModel.options

        firebaseAdapter = MyListAdapter(options, firebaseViewModel)
        recyclerView.adapter = firebaseAdapter

//        viewModel.itemList.observe(this, Observer {
//            adapter.update()
//        })
    }

    override fun onStart() {
        super.onStart()
        firebaseAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        firebaseAdapter?.stopListening()
    }

}