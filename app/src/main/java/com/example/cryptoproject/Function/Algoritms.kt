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
}