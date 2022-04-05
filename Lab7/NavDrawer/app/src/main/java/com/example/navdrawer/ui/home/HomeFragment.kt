package com.example.navdrawer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navdrawer.R
import com.example.navdrawer.databinding.FragmentHomeBinding
//import com.example.tulips.BulbAdapter

class HomeFragment : Fragment() {
    private val dogList = DogData.dogList

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        val adapter = DogAdapter(dogList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        //val adapter = BulbAdapter(bulbList)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
//        val adapter = BulbAdapter(bulbList)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}