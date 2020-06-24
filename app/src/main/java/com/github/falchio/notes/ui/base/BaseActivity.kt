package com.github.falchio.notes.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.falchio.notes.R
import com.firebase.ui.auth.AuthUI
import com.github.falchio.notes.data.errors.NoAuthException
import com.google.android.gms.auth.api.Auth
import kotlinx.android.synthetic.main.activity_main.*


abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    companion object {
        const val RC_SIGN_IN = 4242
    }

    abstract val model: BaseViewModel<T, S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let {
            setContentView(it)
        }

        setSupportActionBar(toolbar)
        model.getViewState().observe(this, Observer { state ->
            state ?: return@Observer
            state.error?.let { e ->
                renderError(e)
                return@Observer
            }
            renderData(state.data)
        })
    }

    abstract fun renderData(data: T)

    protected fun renderError(error: Throwable?) {
        when(error){
            is NoAuthException -> startLogin()
            else ->  error?.message?.let { message ->
                showError(message)
            }
        }
    }

    private fun startLogin(){
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(R.drawable.android_robot)
            .setTheme(R.style.LoginTheme)
            .setAvailableProviders(providers)
            .build()

        startActivityForResult(intent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK){
            finish()
        }
    }


    protected fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
