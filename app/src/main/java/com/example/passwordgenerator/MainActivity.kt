package com.example.passwordgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.slider.Slider

private var lengthSlider: Slider? = null
private var lowercaseCheck: MaterialCheckBox? = null
private var uppercaseCheck: MaterialCheckBox? = null
private var numberCheck: MaterialCheckBox? = null
private var symbolCheck: MaterialCheckBox? = null


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

}