package com.example.passwordgenerator.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.GeneratorFragmentBinding
import com.google.android.material.slider.Slider


class GeneratorFragment : Fragment() {

    private var binding: GeneratorFragmentBinding? = null

    private val sharedViewModel: GeneratorViewModel by activityViewModels()

    private val touchListener: Slider.OnChangeListener =
        Slider.OnChangeListener { slider, value, fromUser ->
            lengthChange(value)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = GeneratorFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        binding?.apply {
            lengthSlider.addOnChangeListener(touchListener)
        }
        generatePassword()
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.generatorFragment = this

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            generatorFragment = this@GeneratorFragment
        }

    }

    fun lengthChange(length: Float) {
        sharedViewModel.setLength(length.toInt())
        generatePassword()
    }

    fun lowercaseChange(isChecked: Boolean) {
        sharedViewModel.setLowercase(isChecked)
        generatePassword()
    }

    fun uppercaseChange(isChecked: Boolean) {
        sharedViewModel.setUppercase(isChecked)
        generatePassword()
    }

    fun numberChange(isChecked: Boolean) {
        sharedViewModel.setNumber(isChecked)
        generatePassword()
    }

    fun symbolChange(isChecked: Boolean) {
        sharedViewModel.setSymbol(isChecked)
        generatePassword()
    }

    fun generatePassword() {
        sharedViewModel.getPassword()
        binding?.resultText?.text = passwordColorString()
    }


    @SuppressLint("ResourceType")
    fun passwordColorString(): SpannableStringBuilder {

        var lcColor = Color.parseColor(resources.getString(R.color.black_text))
        var ucColor = Color.parseColor(resources.getString(R.color.black_text))
        var numColor = Color.parseColor(resources.getString(R.color.black_text))
        var symColor = Color.parseColor(resources.getString(R.color.black_text))

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                lcColor = Color.parseColor(resources.getString(R.color.black_text))
                ucColor = Color.parseColor(resources.getString(R.color.red_text_day))
                numColor = Color.parseColor(resources.getString(R.color.blue_text_day))
                symColor = Color.parseColor(resources.getString(R.color.green_text_day))
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                lcColor = Color.parseColor(resources.getString(R.color.white))
                ucColor = Color.parseColor(resources.getString(R.color.red_text_night))
                numColor = Color.parseColor(resources.getString(R.color.blue_text_night))
                symColor = Color.parseColor(resources.getString(R.color.green_text_night))
            }
        }

        val str = sharedViewModel.generatedPassword.value.toString()
        val spanBuilder = SpannableStringBuilder()

        val splitString = str.toCharArray()
        for (char in splitString) {
            val start = spanBuilder.length
            spanBuilder.append(char)
            if (char.isLowerCase()) {
                spanBuilder.setSpan(
                    ForegroundColorSpan(lcColor),
                    start,
                    spanBuilder.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            } else if (char.isUpperCase()) {
                spanBuilder.setSpan(
                    ForegroundColorSpan(ucColor),
                    start,
                    spanBuilder.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            } else if (char.isDigit()) {
                spanBuilder.setSpan(
                    ForegroundColorSpan(numColor),
                    start,
                    spanBuilder.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            } else {
                spanBuilder.setSpan(
                    ForegroundColorSpan(symColor),
                    start,
                    spanBuilder.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }

        }
        return spanBuilder
    }

    fun copyPassword() {
        val pw = sharedViewModel.savePassword()
        val myClipboard: ClipboardManager =
            activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        //Log.i("INFO", pw.value.toString() + pw.value.toString().length)
        if (pw.value.toString().length < 8) {

            Toast.makeText(context, "No Password Generated", Toast.LENGTH_SHORT).show()
        } else {
            myClipboard.setPrimaryClip(ClipData.newPlainText("generated pw", pw.value.toString()))
            Toast.makeText(context, "Password Copied", Toast.LENGTH_SHORT).show()
        }

    }


}



