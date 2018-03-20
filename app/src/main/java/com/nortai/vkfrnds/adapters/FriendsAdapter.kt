package com.nortai.vkfrnds.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nortai.vkfrnds.R
import com.nortai.vkfrnds.models.FriendModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Atom on 17.03.2018.
 */
class FriendsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mSourceList: ArrayList<FriendModel> = ArrayList()
    private var mFriendsList: ArrayList<FriendModel> = ArrayList()

    fun setupFriends(friendList: ArrayList<FriendModel>) {
        mSourceList.clear()
        mSourceList.addAll(friendList)
        filter(query = "")
    }

    fun filter(query: String) {
        mFriendsList.clear()
        mSourceList.forEach({
            if (it.name.contains(query, ignoreCase = true) || it.surname.contains(query, ignoreCase = true)) {
                mFriendsList.add(it)
            } else {
                it.city?.let { city ->
                    if (city.contains(query, ignoreCase = true)) {
                        mFriendsList.add(it)
                    }
                }
            }
        })
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is FriendsViewHolder) {
            holder.bind(friendModel = mFriendsList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friend, parent, false)
        return FriendsViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mFriendsList.count()
    }

    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var mCivAvatar: CircleImageView = itemView.findViewById(R.id.friend_civ_avatar)
        private var mTxtUsername: TextView = itemView.findViewById(R.id.friend_txt_username)
        private var mTxtCity: TextView = itemView.findViewById(R.id.friend_txt_city)
        private var mImgOnline: View = itemView.findViewById(R.id.friend_img_online)

        fun bind(friendModel: FriendModel) {
            friendModel.avatar?.let { url ->
                Picasso.with(itemView.context)
                        .load(friendModel.avatar)
                        .into(mCivAvatar)
            }
            mTxtCity.text = itemView.context.getString(R.string.friend_no_city)
            friendModel.city?.let { city ->
                mTxtCity.text = city
            }

            mTxtUsername.text = "${friendModel.name} ${friendModel.surname}"
            if (friendModel.isOnline) {
                mImgOnline.visibility = View.VISIBLE
            } else {
                mImgOnline.visibility = View.GONE
            }
        }
    }
}