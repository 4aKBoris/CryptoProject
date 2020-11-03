@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.content.SharedPreferences
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

open class Algoritms {

    protected val cipherStream = setOf(
        "HC128",
        "RC4",
        "HC256",
        "ChaCha",
        "Salsa20",
        "XSalsa20",
        "VMPC",
        "Grainv1",
        "Grain128",
        "Zuc128",
        "Zuc256"
    )


    private val setIV_8 =
        setOf(
            "ChaCha",
            "Salsa20",
            "Grainv1",
            "DES",
            "DESede",
            "Blowfish",
            "XTEA",
            "GOST28147",
            "CAST5",
            "IDEA",
            "Skipjack",
            "TEA",
            "RC2",
            "RC5"
        )
    private val setIV_12 = setOf("Grain128")
    private val setIV_16 = setOf(
        "AES",
        "Rijndael",
        "HC128",
        "HC256",
        "Serpent",
        "SM4",
        "Twofish",
        "Camellia",
        "Noekeon",
        "SEED",
        "CAST6",
        "VMPC",
        "ARIA",
        "GCM",
        "RC6",
        "DSTU7624",
        "GOST3412-2015"
    )

    private val setIV_24 = setOf("XSalsa20")
    private val setIV_32 = setOf("Shacal2", "Threefish-256")
    private val setIV_64 = setOf("Threefish-512")
    private val setIV_128 = setOf("Threefish-1024")

    protected fun keyGenerator(hash: ByteArray, algoritm: String, keysize: Int): SecretKeySpec {
        val digest = MessageDigest.getInstance("SHA-512")
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
        BCM: String
    ): Cipher {
        if (algoritm !in cipherStream && BCM == "ECB") cipher.init(mode, key)
        else if (algoritm !in cipherStream && (BCM == "CCM" || BCM == "OCB")) cipher.init(
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
        return sp.getString("cipherAlgorithm", "AES")!!
    }

    protected fun spCipherCount(sp: SharedPreferences): Int {
        return sp.getInt("cipherCount", 1)
    }

    protected fun spHashAlg(sp: SharedPreferences): String {
        return sp.getString("hashAlgorithm", "SHA-256")!!
    }

    protected fun spHashCount(sp: SharedPreferences): Int {
        return sp.getInt("hashCount", 1)
    }

    protected fun spSalt(sp: SharedPreferences): Boolean {
        return sp.getBoolean("salt", false)
    }

    protected fun spProvider(sp: SharedPreferences): Boolean {
        return sp.getBoolean("provider", false)
    }

    protected fun spCBC(sp: SharedPreferences): String {
        return sp.getString("bcm", "CBC")!!
    }

    protected fun spPadding(sp: SharedPreferences): String {
        return sp.getString("padding", "NoPadding")!!
    }

    protected fun spKeySize(sp: SharedPreferences): Int {
        return sp.getInt("keysize", 32)
    }
}