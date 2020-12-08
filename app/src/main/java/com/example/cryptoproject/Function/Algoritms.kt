@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.content.SharedPreferences
import com.example.cryptoproject.Сonstants.*
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

open class Algoritms {

    protected fun keyGenerator(hash: ByteArray, algoritm: String, keysize: Int): SecretKeySpec {
        val digest = MessageDigest.getInstance(SHA512)
        var hs = digest.digest(hash)
        hs = hs.plus(digest.digest(hs))
        return SecretKeySpec(hs, 0, keysize, algoritm)
    }

    protected fun cipherInit(
        algoritm: String,
        cipher: Cipher,
        key: SecretKey,
        mode: Int,
        iv: ByteArray,
        bcm: String,
    ): Cipher {
        if (algoritm !in cipherStream && bcm == ECB) cipher.init(mode, key)
        else if (algoritm !in cipherStream && (bcm == CCM || bcm == OCB)) cipher.init(
            mode,
            key,
            IvParameterSpec(iv.copyOf(12))
        )
        else when (algoritm) {
            in setIV_8 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(8)))
            in setIV_12 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(12)))
            in setIV_16 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(16)))
            in setIV_24 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(24)))
            in setIV_32 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(32)))
            in setIV_64 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(64)))
            in setIV_128 -> cipher.init(mode, key, IvParameterSpec(iv))
            else -> cipher.init(mode, key)
        }
        return cipher
    }

    protected fun spCipherAlg(sp: SharedPreferences): String {
        return sp.getString(CipherAlgorithm, AES)!!
    }

    protected fun spCipherCount(sp: SharedPreferences): Int {
        return sp.getInt(CipherCount, ONE)
    }

    protected fun spHashAlg(sp: SharedPreferences): String {
        return sp.getString(HashAlgorithm, SHA256)!!
    }

    protected fun spHashCount(sp: SharedPreferences): Int {
        return sp.getInt(HashCount, ONE)
    }

    protected fun spSalt(sp: SharedPreferences): Boolean {
        return sp.getBoolean(Salt, NOT)
    }

    protected fun spCBC(sp: SharedPreferences): String {
        return sp.getString(BCM, CBC)!!
    }

    protected fun spPadding(sp: SharedPreferences): String {
        return sp.getString(Padding, NoPadding)!!
    }

    protected fun spKeySize(sp: SharedPreferences): Int {
        return sp.getInt(KeySize, KEYSIZE)
    }

    protected fun spSignature(sp: SharedPreferences): String {
        return sp.getString(com.example.cryptoproject.Сonstants.Signature, NotUse)!!
    }

    protected fun spCipherPassword(sp: SharedPreferences): Boolean {
        return sp.getBoolean(CipherPassword, NOT)
    }
}