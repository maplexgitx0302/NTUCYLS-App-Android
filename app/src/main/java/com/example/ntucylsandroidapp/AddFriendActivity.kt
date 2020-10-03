package com.example.ntucylsandroidapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.item_add_friend_item.view.*

data class RecyclerClass(val selfID: String, val friendID: String, val friendName: String)

val db = Firebase.firestore

class AddFriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        supportActionBar?.hide()

        val shareColorPreference = getSharedPreferences("Color", MODE_PRIVATE)
        val defaultcolor = shareColorPreference.getInt("darkcolor", resources.getColor(R.color.dark_blue))
        val lightcolor = shareColorPreference.getInt("lightcolor", resources.getColor(R.color.light_blue))
        add_friend_view.setBackgroundColor(defaultcolor)
        window.statusBarColor = lightcolor

        val list = ArrayList<RecyclerClass>()
        val sharePreference = getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val account = sharePreference.getString("Account", "")
        val name = sharePreference.getString("Name", "")

        db.collection("invitation").document(account!!).get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("invitation", "DocumentSnapshot data: ${document.data}")
                val invitationKeys = document.data!!.keys
                for (i in 0 until document.data!!.count()) {
                    val item = RecyclerClass(account!!, invitationKeys.elementAt(i),
                        document.data!![invitationKeys.elementAt(i)].toString()
                    )
                    list += item
                }

                addRecycleView.adapter = RecyclerAdapter(list)
                addRecycleView.layoutManager = LinearLayoutManager(this)
            }
        }
        // search by name
        searchByNameBtn.setOnClickListener {
            if(searchNameBox.text.toString() != "" && searchNameBox.text != null){
                db.collection("profile").whereEqualTo("name", searchNameBox.text.toString())
                    .get().addOnSuccessListener { documents ->
                        if(documents != null){
                            db.collection("friends").document(account).get().addOnSuccessListener { friendlist ->
                                if(friendlist != null){
                                    var checkIsFriend = false
                                    for (friend in documents){
                                        if(friendlist.data?.get(friend.id) != null){
                                            val toast = Toast.makeText(this, "Already Friend", Toast.LENGTH_LONG)
                                            toast.setGravity(Gravity.CENTER,0,0)
                                            toast.show()
                                            checkIsFriend = true
                                        }
                                    }
                                    if(!checkIsFriend){
                                        for (document in documents) {
                                            Log.d("search", "${document.id} => ${document.data}")
                                            db.collection("invitation").document(document.id)
                                                .set(hashMapOf(account to "$account  $name"), SetOptions.merge())
                                        }
                                        val toast = Toast.makeText(this, "Invitation sent", Toast.LENGTH_LONG)
                                        toast.setGravity(Gravity.CENTER,0,0)
                                        toast.show()
                                    }
                                }
                            }
                        }else{
                            val toast = Toast.makeText(this, "Not Found", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER,0,0)
                            toast.show()
                        }
                    }
            }

        }
        //search by ID
        searchByIDBtn.setOnClickListener {
            if(searchIDBox.text.toString() == account){
                val toast = Toast.makeText(this, "Do not add yourself", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
            }else if(searchIDBox.text != null && searchIDBox.text.toString() != ""){
                db.collection("profile").document(searchIDBox.text.toString().toLowerCase())
                    .get().addOnSuccessListener { document ->
                        if (document != null && document.data != null) {
                            Log.d("search", "DocumentSnapshot data: ${document.data}")
                            val initID = searchIDBox.text.toString().toLowerCase()
                            db.collection("invitation").document(initID)
                                .set(hashMapOf(account to "$account  $name"), SetOptions.merge())
                            val toast = Toast.makeText(this, "Invitation sent", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER,0,0)
                            toast.show()
                        } else {
                            Log.d("search", "No such document")
                            val toast = Toast.makeText(this, "Not Found", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER,0,0)
                            toast.show()
                        }
                    }
            }
        }
    }// override end
}

class RecyclerAdapter(private val recyclerList: ArrayList<RecyclerClass>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_add_friend_item,
            parent, false)

        return RecyclerViewHolder(itemView)
    }

    override fun getItemCount() = recyclerList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = recyclerList[position]
        holder.friendAccount.text = currentItem.friendID+"  "+currentItem.friendName
        holder.acceptBut.setOnClickListener {
            if(holder.acceptBut.alpha != 0F){
                holder.acceptBut.alpha = 0F
                holder.refuseBut.alpha = 0F
                // add friends
                db.collection("friends").document(currentItem.friendID)
                    .set(hashMapOf(currentItem.selfID to currentItem.selfID), SetOptions.merge())
                db.collection("friends").document(currentItem.selfID)
                    .set(hashMapOf(currentItem.friendID to currentItem.friendID), SetOptions.merge())

                // delete invitation
                val updates = hashMapOf<String, Any>(currentItem.friendID to FieldValue.delete())
                db.collection("invitation").document(currentItem.selfID).update(updates)
            }
        }
        holder.refuseBut.setOnClickListener {
            if(holder.refuseBut.alpha != 0F){
                holder.acceptBut.alpha = 0F
                holder.refuseBut.alpha = 0F
                db.collection("invitation").document(currentItem.selfID)
                    .update(hashMapOf(currentItem.friendID to FieldValue.delete()) as Map<String, Any>)
            }
        }
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val friendAccount: TextView = itemView.item_add_textview
        val acceptBut: Button = itemView.item_add_accept
        val refuseBut: Button = itemView.item_add_refuse
    }

}