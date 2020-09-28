package com.example.ntucylsandroidapp.ui.themecolors

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ntucylsandroidapp.R
import kotlinx.android.synthetic.main.activity_main.*

class Theme_color : Fragment() {

    private lateinit var viewModel: ThemeColorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.theme_color_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var container =
            activity?.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.container)

        var btn_red = view.findViewById<Button>(R.id.button_red)
        btn_red?.setOnClickListener {
            container?.setBackgroundColor(resources.getColor(R.color.dark_red))
        }
        var btn_grey = view.findViewById<Button>(R.id.button_grey)
        btn_grey?.setOnClickListener {
            container?.setBackgroundColor(resources.getColor(R.color.dark_grey))
        }
        var btn_green = view.findViewById<Button>(R.id.button_green)
        btn_green?.setOnClickListener {
            container?.setBackgroundColor(resources.getColor(R.color.dark_green))
        }
        var btn_yellow = view.findViewById<Button>(R.id.button_yellow)
        btn_yellow?.setOnClickListener {
            container?.setBackgroundColor(resources.getColor(R.color.dark_yellow))
        }
        var btn_blue = view.findViewById<Button>(R.id.button_blue)
        btn_blue?.setOnClickListener {
            container?.setBackgroundColor(resources.getColor(R.color.dark_blue))
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ThemeColorViewModel::class.java)
        // TODO: Use the ViewModel

        // findview.setBackgroundColor(resources.getColor(R.color.dark_red))
    }

}