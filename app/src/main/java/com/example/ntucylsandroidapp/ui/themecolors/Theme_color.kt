package com.example.ntucylsandroidapp.ui.themecolors

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.ntucylsandroidapp.MenuActivity
import com.example.ntucylsandroidapp.R
import com.example.ntucylsandroidapp.SigninActivity


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
            change_color(
                resources.getColor(R.color.dark_red),
                resources.getColor(R.color.light_red)
            )
        }
        var btn_grey = view.findViewById<Button>(R.id.button_grey)
        btn_grey?.setOnClickListener {
            change_color(
                resources.getColor(R.color.dark_grey),
                resources.getColor(R.color.light_grey)
            )
        }
        var btn_green = view.findViewById<Button>(R.id.button_green)
        btn_green?.setOnClickListener {
            change_color(
                resources.getColor(R.color.dark_green),
                resources.getColor(R.color.light_green)
            )
        }
        var btn_yellow = view.findViewById<Button>(R.id.button_yellow)
        btn_yellow?.setOnClickListener {
            change_color(
                resources.getColor(R.color.dark_yellow),
                resources.getColor(R.color.light_yellow)
            )
        }
        var btn_blue = view.findViewById<Button>(R.id.button_blue)
        btn_blue?.setOnClickListener {
            change_color(
                resources.getColor(R.color.dark_blue),
                resources.getColor(R.color.light_blue)
            )
        }


    }

    private fun change_color(darkcolor: Int, lightcolor: Int){
        var container =
            activity?.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.container)
        container?.setBackgroundColor(darkcolor)
        var signinview =
            activity?.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.signinview)
        signinview?.setBackgroundColor(darkcolor)
        var registerview =
            activity?.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.registerview)
        registerview?.setBackgroundColor(darkcolor)
        // action bar and status bar
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                lightcolor
            )
        )
        (activity as AppCompatActivity).window.statusBarColor = lightcolor
        //save color to local
        val sharePreference = activity?.getSharedPreferences("Color", Context.MODE_PRIVATE)
        sharePreference?.edit()?.putInt("darkcolor", darkcolor)?.apply()
        sharePreference?.edit()?.putInt("lightcolor", lightcolor)?.apply()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ThemeColorViewModel::class.java)
        // TODO: Use the ViewModel

        // findview.setBackgroundColor(resources.getColor(R.color.dark_red))
    }

}