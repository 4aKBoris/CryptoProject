@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.content.SharedPreferences
import com.example.cryptoproject.R
import com.example.cryptoproject.Сonstants.*

fun spCipherAlg(sp: SharedPreferences): String {
    return sp.getString(CipherAlgorithm, AES)!!
}

fun spCipherCount(sp: SharedPreferences): Int {
    return sp.getInt(CipherCount, ONE)
}

fun spHashAlg(sp: SharedPreferences): String {
    return sp.getString(HashAlgorithm, SHA256)!!
}

fun spHashCount(sp: SharedPreferences): Int {
    return sp.getInt(HashCount, ONE)
}

fun spSalt(sp: SharedPreferences): Boolean {
    return sp.getBoolean(Salt, NOT)
}

fun spCBC(sp: SharedPreferences): String {
    return sp.getString(BCM, CBC)!!
}

fun spPadding(sp: SharedPreferences): String {
    return sp.getString(Padding, NoPadding)!!
}

fun spKeySize(sp: SharedPreferences): Int {
    return sp.getInt(KeySize, KEYSIZE)
}

fun spSignature(sp: SharedPreferences): String {
    return sp.getString(com.example.cryptoproject.Сonstants.Signature, NotUse)!!
}

fun spCipherPassword(sp: SharedPreferences): Boolean {
    return sp.getBoolean(CipherPassword, NOT)
}

fun spPasswordFlag(sp: SharedPreferences): Boolean {
    return sp.getBoolean(PasswordFlag, NOT)
}

fun spSecond(sp: SharedPreferences): Boolean {
    return sp.getBoolean(SecordPassword, NOT)
}

fun spDeleteFile(sp: SharedPreferences) : Boolean {
    return sp.getBoolean(DeleteFile, NOT)
}

