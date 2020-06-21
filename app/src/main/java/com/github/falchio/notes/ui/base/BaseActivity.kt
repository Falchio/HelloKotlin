package com.github.falchio.notes.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*


abstract class BaseActivity <T, S: BaseViewState<T>>: AppCompatActivity() {
    abstract val viewModel: BaseViewModel<T,S>
    abstract val layoutRes: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        setSupportActionBar(toolbar)
        viewModel.getViewState().observe(this, Observer {state ->
            state ?: return@Observer //if (state == null) return
            state.error?.let {error ->
                renderError(error)
                return@Observer
            }
            renderData(state.data)
        })
    }

    abstract fun renderData(data: T)

    protected fun renderError(error: Throwable?){
        error?.message?.let {message->
            showError(message)
        }
    }

    protected fun showError(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
