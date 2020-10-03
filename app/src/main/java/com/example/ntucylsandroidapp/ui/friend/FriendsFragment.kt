package com.example.ntucylsandroidapp.ui.friend

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ntucylsandroidapp.*
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.item_friend_item.view.*

data class FriendRecyclerClass(val friendID: String)

class FriendsFragment : Fragment() {

    private lateinit var friendsViewModel: FriendsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        friendsViewModel =
                ViewModelProviders.of(this).get(FriendsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_friends, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addFriendBtn = view.findViewById<android.widget.Button>(R.id.addFriendBtn)
        addFriendBtn?.setOnClickListener {
            val intent = Intent(activity, AddFriendActivity::class.java)
            startActivity(intent)
        }

        val list = ArrayList<FriendRecyclerClass>()
        println(global_account)
        db.collection("friends").document(global_account).get().addOnSuccessListener { document ->
            if(document != null){
                if(document.data?.count()!! >= 1){
                    val friendKeys = document.data!!.keys
                    for (i in 0 until document.data!!.count()) {
                        db.collection("profile").document(friendKeys.elementAt(i)).get().addOnSuccessListener { profiles->
                            if (profiles != null){
                                val friendNameGrade = "" + profiles.data!!["name"] + "  " + profiles.data!!["grade"]
                                val item = FriendRecyclerClass(friendNameGrade)
                                list += item
                            }
                            if (i == document.data!!.count()-1){
                                friendRecycleView.adapter = FriendRecyclerAdapter(list)
                                friendRecycleView.layoutManager = LinearLayoutManager(context)
                            }
                        }
                    }
                }
            }
        }

    }
}

class FriendRecyclerAdapter(private  val recyclerList: List<FriendRecyclerClass>) : RecyclerView.Adapter<FriendRecyclerAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_friend_item,
            parent, false)

        return RecyclerViewHolder(itemView)
    }

    override fun getItemCount() = recyclerList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = recyclerList[position]
        holder.friendAccount.text = currentItem.friendID
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val friendAccount: Button = itemView.friend_information
    }

}