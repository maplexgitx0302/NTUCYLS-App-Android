package com.example.ntucylsandroidapp.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ntucylsandroidapp.R
import com.example.ntucylsandroidapp.global_fragment
import com.example.ntucylsandroidapp.ui.friend.FriendRecyclerAdapter
import com.example.ntucylsandroidapp.ui.friend.FriendRecyclerClass
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_friend_item.view.*
import kotlinx.android.synthetic.main.item_home_item.view.*

data class MingChinRecyclerClass(val imageresourse: Int)

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
        // change view
        if (global_fragment == "theme_color"){
            view.findNavController().navigate(R.id.navigation_setting, null)
            global_fragment = ""
        }else if (global_fragment == "nav_profile"){
            view.findNavController().navigate(R.id.navigation_setting, null)
            global_fragment = ""
        }

        val list = ArrayList<MingChinRecyclerClass>()
        for (i in 1..61){
            val strName = "android_57mingchin$i"
            val imgID = resources.getIdentifier(strName, "drawable", activity?.packageName)

            val item = MingChinRecyclerClass(imgID)
            list += item
        }
        mingchinRecycleView.adapter = MingChinRecyclerAdapter(list)
        mingchinRecycleView.layoutManager = LinearLayoutManager(context)


    }

}

class MingChinRecyclerAdapter(private  val recyclerList: List<MingChinRecyclerClass>) : RecyclerView.Adapter<MingChinRecyclerAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_item,
            parent, false)

        return RecyclerViewHolder(itemView)
    }

    override fun getItemCount() = recyclerList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = recyclerList[position]
        holder.image.setImageResource(currentItem.imageresourse)

    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.mingchinimageview
    }

}