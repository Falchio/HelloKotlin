package com.falchio.notes

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        viewModel.viewState().observe(this, Observer { Toast.makeText(this,it,Toast.LENGTH_LONG ).show() })

        val button = findViewById<Button>(R.id.button)
        val text = findViewById<TextView>(R.id.text)

        button.setOnClickListener {
            text.text = "Goodbye, world!"
        }
    }
}
