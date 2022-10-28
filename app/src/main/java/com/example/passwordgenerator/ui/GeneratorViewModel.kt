package com.example.passwordgenerator.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GeneratorViewModel : ViewModel() {

    private val _passwordLength = MutableLiveData<Int>()
    val passwordLength: LiveData<Int> = _passwordLength

    private val _includeLowercase = MutableLiveData<Boolean>()
    val includeLowercase: LiveData<Boolean> = _includeLowercase

    private val _includeUppercase = MutableLiveData<Boolean>()
    val includeUppercase: LiveData<Boolean> = _includeUppercase

    private val _includeNumber = MutableLiveData<Boolean>()
    val includeNumber: LiveData<Boolean> = _includeNumber

    private val _includeSymbol = MutableLiveData<Boolean>()
    val includeSymbol: LiveData<Boolean> = _includeSymbol

    private val _generatedPassword = MutableLiveData<String>()
    val generatedPassword: LiveData<String> = _generatedPassword

    init {
        reset()
    }


    private fun generatePassword() {
        //Log.i("INFO", "Lower ${includeLowercase.value} Upper ${includeUppercase.value} Number ${includeNumber.value} Symbol ${includeSymbol.value} Length ${passwordLength.value}")
        var tempPassword: String = ""
        while (tempPassword.length < passwordLength.value!!) {
            var randomNum = (1..4).random()
            //Log.i("INFO", "test$randomNum")
            when (randomNum) {
                1 -> if (includeLowercase.value!!) {
                    //Log.i("INFO","testL")
                    tempPassword += applyLowercase()
                }
                2 -> if (includeUppercase.value!!) {
                    //Log.i("INFO","testU")
                    tempPassword += applyUppercase()
                }
                3 -> if (includeNumber.value!!) {
                    //Log.i("INFO","testN")
                    tempPassword += applyNumber()
                }
                else -> if (includeSymbol.value!!) {
                    //Log.i("INFO","testS")
                    tempPassword += applySymbol()
                }
            }
            //Log.i("INFO", tempPassword.length.toString() + " " + passwordLength.value)
        }

        if (includeLowercase.value!!) {
            if (!tempPassword.contains("[a-z]".toRegex())) {
                tempPassword += applyLowercase()
                tempPassword.drop(1)
            }
        }
        if (includeUppercase.value!!) {
            if (!tempPassword.contains("[A-Z]".toRegex())) {
                tempPassword += applyUppercase()
                tempPassword.drop(1)
            }
        }
        if (includeNumber.value!!) {
            if (!tempPassword.contains("[0-9]".toRegex())) {
                tempPassword += applyNumber()
                tempPassword.drop(1)
            }
        }
        if (includeSymbol.value!!) {
            if (!tempPassword.contains("[!@#$%^&*(){}\\[\\]=<>/,.]".toRegex())) {
                tempPassword += applyLowercase()
                tempPassword.drop(1)
            }
        }

        _generatedPassword.value = tempPassword
        _generatedPassword.value?.let { Log.i("INFO", it) }
    }


    private fun applyLowercase(): String {
        return ('a'..'z').random().toString()
    }

    private fun applyUppercase(): String {
        return ('A'..'Z').random().toString()
    }

    private fun applyNumber(): String {
        return ('0'..'9').random().toString()
    }

    private fun applySymbol(): String {
        val symbolArray = arrayListOf<Char>(
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*',
            '(',
            ')',
            '{',
            '}',
            '[',
            ']',
            '=',
            '<',
            '>',
            '/',
            ',',
            '.'
        )
        return symbolArray.random().toString()
    }

    fun getPassword() {
        generatePassword()
    }

    fun setLength(int: Int) {
        _passwordLength.value = int
    }

    fun setLowercase(boolean: Boolean) {
        _includeLowercase.value = boolean
        Log.i("INFO", "Set lowercase ${includeLowercase.value}")
    }

    fun setUppercase(boolean: Boolean) {
        _includeUppercase.value = boolean
    }

    fun setNumber(boolean: Boolean) {
        _includeNumber.value = boolean
    }

    fun setSymbol(boolean: Boolean) {
        _includeSymbol.value = boolean
    }

    private fun reset() {
        _passwordLength.value = 8
        _includeLowercase.value = true
        _includeUppercase.value = false
        _includeNumber.value = false
        _includeSymbol.value = false
        _generatedPassword.value = ""
    }

    fun savePassword(): LiveData<String> {
        return generatedPassword
    }
}