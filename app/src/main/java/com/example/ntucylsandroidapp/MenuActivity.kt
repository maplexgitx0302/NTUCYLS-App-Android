package com.example.ntucylsandroidapp

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ntucylsandroidapp.ui.setting.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_friends.*

var global_fragment = ""

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val sharePreference = getSharedPreferences("Color", MODE_PRIVATE)
        val darkcolor = sharePreference.getInt("darkcolor", resources.getColor(R.color.dark_blue))
        val lightcolor = sharePreference.getInt("lightcolor", resources.getColor(R.color.light_blue))

        container.setBackgroundColor(darkcolor)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(lightcolor))
        window.statusBarColor = lightcolor

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_friends, R.id.navigation_setting
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

