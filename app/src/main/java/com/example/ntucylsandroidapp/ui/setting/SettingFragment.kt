package com.example.ntucylsandroidapp.ui.setting

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.ntucylsandroidapp.R
import com.example.ntucylsandroidapp.SigninActivity
import com.example.ntucylsandroidapp.global_fragment
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharePreference = activity?.getSharedPreferences("Color", AppCompatActivity.MODE_PRIVATE)
        val darkcolor = sharePreference?.getInt("darkcolor", resources.getColor(R.color.dark_blue))
        val lightcolor = sharePreference?.getInt("lightcolor", resources.getColor(R.color.light_blue))

        val button_theme_color = view.findViewById<android.widget.Button>(R.id.button_theme_colors)
        val button_logout = view.findViewById<android.widget.Button>(R.id.button_logout)
        val button_personal_info = view.findViewById<android.widget.Button>(R.id.button_personal_info)
        val button_mm = view.findViewById<android.widget.Button>(R.id.button_mm)
        val button_mr = view.findViewById<android.widget.Button>(R.id.button_mr)
        val button_ml = view.findViewById<android.widget.Button>(R.id.button_ml)
        val button_bl = view.findViewById<android.widget.Button>(R.id.button_bl)
        val button_bm = view.findViewById<android.widget.Button>(R.id.button_bm)
        val button_br = view.findViewById<android.widget.Button>(R.id.button_br)

        if (lightcolor != null) {
            button_theme_color.setBackgroundColor(lightcolor)
            button_logout.setBackgroundColor(lightcolor)
            button_personal_info.setBackgroundColor(lightcolor)
            button_mr.setBackgroundColor(lightcolor)
            button_ml.setBackgroundColor(lightcolor)
            button_bl.setBackgroundColor(lightcolor)
            button_bm.setBackgroundColor(lightcolor)
            button_br.setBackgroundColor(lightcolor)
        }



        button_theme_color?.setOnClickListener {
            view.findNavController().navigate(R.id.theme_color, null)
            global_fragment = "theme_color"
        }

        button_logout.setOnClickListener {
            val intent = Intent(activity, SigninActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            activity?.finish()
        }

        button_personal_info.setOnClickListener {
            view.findNavController().navigate(R.id.nav_profile, null)
            global_fragment = "nav_profile"
        }

        var width = Resources.getSystem().displayMetrics.widthPixels
        var scale_constant = 3.3
        button_theme_color.layoutParams.width = (width/scale_constant).toInt()
        button_theme_color.layoutParams.height = (width/scale_constant).toInt()
        button_personal_info.layoutParams.width = (width/scale_constant).toInt()
        button_personal_info.layoutParams.height = (width/scale_constant).toInt()
        button_logout.layoutParams.width = (width/scale_constant).toInt()
        button_logout.layoutParams.height = (width/scale_constant).toInt()
        button_ml.layoutParams.width = (width/scale_constant).toInt()
        button_ml.layoutParams.height = (width/scale_constant).toInt()
        button_mm.layoutParams.width = (width/scale_constant).toInt()
        button_mm.layoutParams.height = (width/scale_constant).toInt()
        button_mr.layoutParams.width = (width/scale_constant).toInt()
        button_mr.layoutParams.height = (width/scale_constant).toInt()
        button_bl.layoutParams.width = (width/scale_constant).toInt()
        button_bl.layoutParams.height = (width/scale_constant).toInt()
        button_bm.layoutParams.width = (width/scale_constant).toInt()
        button_bm.layoutParams.height = (width/scale_constant).toInt()
        button_br.layoutParams.width = (width/scale_constant).toInt()
        button_br.layoutParams.height = (width/scale_constant).toInt()
    }
}