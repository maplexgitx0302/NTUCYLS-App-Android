package com.example.ntucylsandroidapp.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.ntucylsandroidapp.R
import com.example.ntucylsandroidapp.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button_theme_color = view.findViewById<android.widget.Button>(R.id.button_theme_colors)

        button_theme_color?.setOnClickListener {
            view.findNavController().navigate(R.id.theme_color, null)
        }

        var width = Resources.getSystem().displayMetrics.widthPixels
        button_theme_color.layoutParams.width = width/3
        button_theme_color.layoutParams.height = width/3
        button_personal_info.layoutParams.width = width/3
        button_personal_info.layoutParams.height = width/3
        button_logout.layoutParams.width = width/3
        button_logout.layoutParams.height = width/3
        button_ml.layoutParams.width = width/3
        button_ml.layoutParams.height = width/3
        button_mm.layoutParams.width = width/3
        button_mm.layoutParams.height = width/3
        button_mr.layoutParams.width = width/3
        button_mr.layoutParams.height = width/3
        button_bl.layoutParams.width = width/3
        button_bl.layoutParams.height = width/3
        button_bm.layoutParams.width = width/3
        button_bm.layoutParams.height = width/3
        button_br.layoutParams.width = width/3
        button_br.layoutParams.height = width/3
    }
}