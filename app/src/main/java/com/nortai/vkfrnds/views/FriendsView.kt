package com.nortai.vkfrnds.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.nortai.vkfrnds.models.FriendModel

/**
 * Created by Atom on 17.03.2018.
 */
@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FriendsView: MvpView {
    fun showError(textResource: Int)
    fun setupEmptyList()
    fun setupFriendsList(friendsList: ArrayList<FriendModel>)
    fun startLoading()
    fun endLoading()
}