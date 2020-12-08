@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.annotation.SuppressLint
import com.example.cryptoproject.Сonstants.*
import java.security.*
import javax.crypto.Cipher.getInstance
import kotlin.random.Random

class MetaDataInput(
    private var arr: ByteArray,
    private val password: String,
    private val hash_algorithm: String,
    private val hash_count: Int,
    private val cipher_algorithm: String,
    private val cipher_bcm: String,
    private val cipher_padding: String,
    private val cipher_count: Int,
    private val iv: ByteArray,
    private val keysize: Int,
    private val signature: String,
    private val cipherPassword: Boolean,
) {

    private var salt: ByteArray? = null
    private var zeroByte = 0

    private lateinit var publicKey: PublicKey
    private lateinit var password_key_store: String

    fun setSalt(salt: ByteArray) {
        this.salt = salt
    }

    fun setZeroByte(zeroByte: Int) {
        this.zeroByte = zeroByte
    }

    fun setPublicKey(publicKey: PublicKey) {
        this.publicKey = publicKey
    }

    fun setPasswordKeyStore(password_key_store: String) {
        this.password_key_store = password_key_store
    }

    @SuppressLint("SdCardPath")
    fun metaData(): ByteArray {
        meta.run {
            if (cipherPassword) Encrypt()
            else add(random(false))
            add(hashAlg.indexOf(hash_algorithm).toByte())
            if (hash_count > 127) {
                add(random(true))
                add((hash_count / 128).toByte())
                add((hash_count % 128).toByte())
            } else {
                add(random(false))
                add(hash_count.toByte())
                add(rnd.nextInt().toByte())
            }
            if (salt != null) {
                add(random(true))
                addAll(salt!!.toList())
            } else add(random(false))
            add(cipherAlg.indexOf(cipher_algorithm).toByte())
            add(cipher_count.toByte())
            if (cipher_algorithm !in cipherStream) {
                add(cipherBcm.indexOf(cipher_bcm).toByte())
                add(cipherPadding.indexOf(cipher_padding).toByte())
            }
            addAll(iv.toList())
            add(keysize.toByte())
            add(zeroByte.toByte())
        }
        arr = meta.toByteArray().plus(arr)
        if (signature != "Не использовать") {
            val sign = Signature(arr, signature, password_key_store)
            arr = sign.SignEncrypt()
        }
        return byteArrayOf(sign.indexOf(signature).toByte()).plus(arr)
    }

    private fun Encrypt() {
        meta.add(random(true))
        val cipher = getInstance("RSA")
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, publicKey)
        meta.addAll(cipher.doFinal(password.toByteArray()).toList())
    }

    private fun random(flag: Boolean): Byte {
        var fl = rnd.nextInt()
        while ((fl % 2 == 0) == flag) {
            fl = rnd.nextInt()
        }
        return fl.toByte()
    }

    companion object {
        private val rnd = Random
        private var meta = ArrayList<Byte>()
    }
}