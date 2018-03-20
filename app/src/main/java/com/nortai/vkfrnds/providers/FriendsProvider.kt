package com.nortai.vkfrnds.providers

import android.os.Handler
import com.arellomobile.mvp.MvpPresenter
import com.google.gson.JsonParser
import com.nortai.vkfrnds.R
import com.nortai.vkfrnds.models.FriendModel
import com.nortai.vkfrnds.presenters.FriendsPresenter
import com.vk.sdk.api.*

/**
 * Created by Atom on 17.03.2018.
 */
class FriendsProvider (var presenter: FriendsPresenter) {
    fun testLoadFriends(hasFriends: Boolean) {
        Handler().postDelayed({
            val friendsList: ArrayList<FriendModel> = ArrayList()
            if (hasFriends) {
                val friend1 = FriendModel(name = "Cara", surname = "Delevingne", city = "New York", avatar = "https://pp.userapi.com/c622326/v622326181/3a978/UTpw6EZREIc.jpg", isOnline = true)
                friendsList.add(friend1)
            }
            presenter.friendsLoaded(friendsList = friendsList)
        }, 2000)
    }

    fun loadFriends() {
        val request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "city, country, photo_100, online"))
        request.executeWithListener(object: VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)
                val jsonParser = JsonParser()
                val parsedJson = jsonParser.parse(response.json.toString()).asJsonObject
                val friendsList: ArrayList<FriendModel> = ArrayList()

                parsedJson.get("response").asJsonObject.getAsJsonArray("items").forEach {
                    val city = if (it.asJsonObject.get("city") == null) {
                        null
                    } else {
                        it.asJsonObject.get("city").asJsonObject.get("title").asString
                    }
                    val friend = FriendModel(
                            name = it.asJsonObject.get("first_name").asString,
                            surname = it.asJsonObject.get("last_name").asString,
                            city = city,
                            avatar = it.asJsonObject.get("photo_100").asString,
                            isOnline = it.asJsonObject.get("online").asInt == 1
                    )
                    friendsList.add(friend)
                }
                presenter.friendsLoaded(friendsList = friendsList)
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(textResource = R.string.friends_error_loading)
            }
        })
    }
}