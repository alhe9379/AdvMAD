package com.example.navdrawer.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navdrawer.MainActivity
import com.example.navdrawer.R
import com.google.android.material.switchmaterial.SwitchMaterial


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//lateinit var themeSwitch: SwitchMaterial
/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //val intent = Intent(getActivity(), MainActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //https://stackoverflow.com/questions/6495898/findviewbyid-in-fragment
        val themeSwitch: SwitchMaterial = requireView().findViewById<SwitchMaterial>(R.id.themeSwitch)
        //https://stackoverflow.com/questions/11278507/android-widget-switch-on-off-event-listener
        themeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            //Intent intent = new Intent(getActivity(), MainActivity.class)
            if(isChecked){
                //https://www.youtube.com/watch?v=i-K5uBwEt6o&ab_channel=TechSanjeetHindi
                val intent = Intent(getActivity(), MainActivity::class.java)
                intent.putExtra("key", isChecked)
                startActivity(intent)
                //super.setTheme(R.style.BlackTheme);
                Log.i("test", "hi")
            }
        }
    }




//    fun sendData(){
//        val i: Intent = Intent
//        val intent = Intent(this, MainActivity::class.java)
//    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}