package com.nortai.vkfrnds.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.nortai.vkfrnds.R
import com.nortai.vkfrnds.presenters.LoginPresenter
import com.nortai.vkfrnds.views.LoginView
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk


class LoginActivity : MvpAppCompatActivity(), LoginView {

    private lateinit var mTxtHello: TextView
    private lateinit var mBtnEnter: Button
    private lateinit var mCpvWait: CircularProgressView

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mTxtHello = findViewById(R.id.login_textView)
        mBtnEnter = findViewById(R.id.login_button)
        mCpvWait = findViewById(R.id.progress_view)

        mBtnEnter.setOnClickListener {
            VKSdk.login(this@LoginActivity, VKScope.FRIENDS);
        }

        /*val fingerprints = VKUtil.getCertificateFingerprint(this, this.packageName)
        Toast.makeText(applicationContext, fingerprints[0], Toast.LENGTH_LONG).show()*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (loginPresenter.login(requestCode = requestCode, resultCode = resultCode, data = data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startLoading() {
        mBtnEnter.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mBtnEnter.visibility = View.VISIBLE
        mCpvWait.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun openFriends() {
        startActivity(Intent(applicationContext, FriendsActivity::class.java))
    }
}