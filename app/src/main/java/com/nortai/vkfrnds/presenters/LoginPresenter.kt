package com.nortai.vkfrnds.presenters

import android.content.Intent
import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nortai.vkfrnds.R
import com.nortai.vkfrnds.views.LoginView
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError

/**
 * Created by Atom on 17.03.2018.
 */
@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {
    fun testLogin(isSuccess: Boolean) {
        viewState.startLoading()
        Handler().postDelayed({
            viewState.endLoading()
            if (isSuccess) {
                viewState.openFriends()
            } else {
                viewState.showError(textResource = R.string.login_error_credentials)
            }
        }, 500)
    }

    fun login(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken?) {
                viewState.openFriends()
            }

            override fun onError(error: VKError?) {
                viewState.showError(textResource = R.string.login_error_credentials)
            }
        })) {
            return false
        }
        return true
    }
}