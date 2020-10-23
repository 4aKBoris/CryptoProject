package com.example.cryptoproject.Function

import android.content.SharedPreferences
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

open class Algoritms {

    protected val cipherStream = setOf(
        "AESWrap",
        "ARIAWrap",
        "CamelliaWrap",
        "DESedeWrap",
        "DSTU7624Wrap",
        "SEEDWrap",
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

    protected val cbc128 = setOf("CCM", "GCM", "OCB")

    protected val cipher64 = setOf(
        "Blowfish",
        "CAST5",
        "DES",
        "DESede",
        "GOST28147",
        "IDEA",
        "RC2",
        "RC5",
        "Skipjack",
        "TEA",
        "XTEA"
    )

    protected val cipher128 = setOf(
        "AES",
        "ARIA",
        "Camellia",
        "CAST6",
        "DSTU7624",
        "Noekeon",
        "RC5-64",
        "RC-6",
        "Rijndael",
        "SEED",
        "Serpent",
        "SM4",
        "Twofish"
    )

    private val set64bit = setOf("DES")
    private val set80bit = setOf("Grainv1")
    private val set128bit =
        setOf(
            "CAST5",
            "IDEA",
            "Noekeon",
            "SEED",
            "Skipjack",
            "SM4",
            "TEA",
            "XTEA",
            "HC128",
            "Grain128",
            "SEEDWrap"
        )
    private val set192bit = setOf("DESede", "DESedeWrap")
    private val set256bit = setOf(
        "AES",
        "AESWrap",
        "ARIAWrap",
        "CamelliaWrap",
        "GOST28147",
        "Blowfish",
        "Camellia",
        "CAST6",
        "Rijndael",
        "Serpent",
        "Twofish",
        "HC256",
        "ChaCha",
        "Salsa20",
        "XSalsa20",
        "DSTU7624",
        "DSTU7624Wrap",
        "GCM",
        "RC5-64",
        "Threefish-256"
    )
    private val setIV_8 = setOf("ChaCha", "Salsa20", "Grainv1", "DES", "DESede", "Blowfish", "XTEA", "GOST28147")
    private val setIV_12 = setOf("Grain128")
    private val setIV_16 = setOf(
        "AES",
        "IDEA",
        "HC128",
        "HC256",
        "Shacal2",
        "Serpent",
        "Skipjack",
        "SM4",
        "TEA",
        "Twofish",
        "Camellia",
        "Noekeon",
        "SEED",
        "CAST5",
        "CAST6",
        "VMPC",
    )
    private val setIV_24 = setOf("XSalsa20")

    protected fun keyGenerator(hash: ByteArray, algoritm: String): SecretKeySpec {
        val digest = MessageDigest.getInstance("SHA-512")
        var hs = digest.digest(hash)
        hs = when (algoritm) {
            in set256bit -> hs.copyOf(32)
            in set128bit -> hs.copyOf(16)
            in set192bit -> hs.copyOf(24)
            in set64bit -> hs.copyOf(8)
            in set80bit -> hs.copyOf(10)
            else -> hs
        }
        return SecretKeySpec(hs, algoritm)
    }

    protected fun cipherInit(
        algoritm: String,
        cipher: Cipher,
        key: SecretKey,
        mode: Int,
        iv: ByteArray,
        CBC: String?
    ): Cipher {
        if (CBC == null || CBC == "ECB") cipher.init(mode, key)
        else when (algoritm) {
            in setIV_8 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(8)))
            in setIV_12 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(12)))
            in setIV_16 -> cipher.init(mode, key, IvParameterSpec(iv.copyOf(16)))
            in setIV_24 -> cipher.init(mode, key, IvParameterSpec(iv))
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
}