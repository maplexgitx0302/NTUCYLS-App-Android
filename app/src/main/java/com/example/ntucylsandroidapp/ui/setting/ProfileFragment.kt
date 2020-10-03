package com.example.ntucylsandroidapp.ui.setting

import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ntucylsandroidapp.*
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharePreference = activity?.getSharedPreferences("Color", AppCompatActivity.MODE_PRIVATE)
        val darkcolor = sharePreference?.getInt("darkcolor", resources.getColor(R.color.dark_blue))
        val lightcolor = sharePreference?.getInt("lightcolor", resources.getColor(R.color.light_blue))

        var upload_button = view.findViewById<Button>(R.id.upload_button)
        var edit_button = view.findViewById<Button>(R.id.edit_button)
        var image_button = view.findViewById<Button>(R.id.image_button)

        var profileAccountTextView = view.findViewById<TextView>(R.id.profileAccountTextView)
        profileAccountTextView.text = global_account
        var name_textfield = view.findViewById<TextView>(R.id.name_textfield)
        name_textfield.text = global_name
        var grade_textfield = view.findViewById<TextView>(R.id.grade_textfield)
        grade_textfield.text = global_grade
        var home_textfield = view.findViewById<TextView>(R.id.home_textfield)
        home_textfield.text = global_home
        var department_textfield = view.findViewById<TextView>(R.id.department_textfield)
        department_textfield.text = global_group
        var count_textfield = view.findViewById<TextView>(R.id.count_textfield)
        count_textfield.text = global_count
        var epoch_textfield = view.findViewById<TextView>(R.id.epoch_textfield)
        epoch_textfield.text = global_epoch
        val textfield_list = arrayOf(name_textfield,grade_textfield,home_textfield,department_textfield,
        count_textfield,epoch_textfield)

        if (lightcolor != null) {
            upload_button.setBackgroundColor(lightcolor)
            edit_button.setBackgroundColor(lightcolor)
            image_button.setBackgroundColor(lightcolor)
        }
        var edit_bool = false
        edit_button.setOnClickListener {
            if (edit_bool == false){
                edit_button.text = "Done"
                edit_button.setTextColor(0xffff0000.toInt())
                edit_bool = true
                for(i in 0 until textfield_list.count()){
                    textfield_list[i].isEnabled = true
                }
            }else if(edit_bool == true){
                global_name = name_textfield.text.toString()
                global_grade = grade_textfield.text.toString()
                global_count = count_textfield.text.toString()
                global_epoch = epoch_textfield.text.toString()
                global_group = department_textfield.text.toString()
                global_home = home_textfield.text.toString()
                val db = Firebase.firestore
                db.collection("profile").document(global_account).set(hashMapOf(
                    "name" to global_name,
                    "count" to global_count,
                    "epoch" to global_epoch,
                    "group" to global_group,
                    "home" to global_home,
                    "grade" to global_grade
                ), SetOptions.merge())
                edit_button.text = "Edit"
                edit_button.setTextColor(0xffffffff.toInt())
                edit_bool = false
                for(i in 0 until textfield_list.count()){
                    textfield_list[i].isEnabled = false
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}