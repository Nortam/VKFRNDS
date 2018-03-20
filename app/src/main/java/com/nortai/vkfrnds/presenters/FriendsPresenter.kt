package com.nortai.vkfrnds.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nortai.vkfrnds.R
import com.nortai.vkfrnds.models.FriendModel
import com.nortai.vkfrnds.providers.FriendsProvider
import com.nortai.vkfrnds.views.FriendsView

/**
 * Created by Atom on 17.03.2018.
 */
@InjectViewState
class FriendsPresenter: MvpPresenter<FriendsView>() {
    fun loadFriends() {
        viewState.startLoading()
        FriendsProvider(presenter = this).loadFriends()
    }

    fun friendsLoaded(friendsList: ArrayList<FriendModel>) {
        viewState.endLoading()
        if (friendsList.size == 0) {
            viewState.setupEmptyList()
            viewState.showError(textResource = R.string.friends_no_items)
        } else {
            viewState.setupFriendsList(friendsList = friendsList)
        }
    }

    fun showError(textResource: Int) {
        viewState.showError(textResource = textResource)
    }
}