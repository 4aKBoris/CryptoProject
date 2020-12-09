@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.annotation.SuppressLint
import com.example.cryptoproject.DataObject.MetaData
import com.example.cryptoproject.Сonstants.*
import java.security.PublicKey
import javax.crypto.Cipher.getInstance
import kotlin.random.Random

class MetaDataInput(
    private val meta : MetaData
) {

    private lateinit var publicKey: PublicKey
    private lateinit var password_key_store: String

    private val rnd = Random
    private var mas = byteArrayOf()

    fun setPublicKey(publicKey: PublicKey) {
        this.publicKey = publicKey
    }

    fun setPasswordKeyStore(password_key_store: String) {
        this.password_key_store = password_key_store
    }

    @SuppressLint("SdCardPath")
    fun metaData(): ByteArray {
        meta.run {
            if (cipher_password) Encrypt()
            else plus(random(false))
            plus(hashAlg.indexOf(hash_alg).toByte())
            if (hash_count > BlockSize - 1) {
                plus(random(true))
                plus((hash_count / BlockSize).toByte())
                plus((hash_count % BlockSize).toByte())
            } else {
                plus(random(false))
                plus(hash_count.toByte())
                plus(rnd.nextInt().toByte())
            }
            if (flag_salt) {
                plus(random(true))
                plus(salt!!)
            } else plus(random(false))
            plus(cipherAlg.indexOf(cipher_alg).toByte())
            plus(cipher_count.toByte())
            if (cipher_alg !in cipherStream) {
                plus(cipherBcm.indexOf(bcm).toByte())
                plus(cipherPadding.indexOf(padding).toByte())
            }
            plus(iv)
            plus(keysize.toByte())
            plus(zeroByte.toByte())
            array = mas.plus(array)
            if (signature != NotUse) {
                val sign = Signature(array, signature, password_key_store)
                array = sign.SignEncrypt()
            }
            return byteArrayOf(sign.indexOf(signature).toByte()).plus(array)
        }
    }

    private fun Encrypt() {
        plus(random(true))
        val cipher = getInstance(RSA)
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, publicKey)
        plus(cipher.doFinal(meta.password.toByteArray()))
    }

    private fun random(flag: Boolean): Byte {
        var fl = rnd.nextInt()
        while ((fl % 2 == 0) == flag) {
            fl = rnd.nextInt()
        }
        return fl.toByte()
    }

    private fun plus(a: ByteArray) {
        mas = mas.plus(a)
    }

    private fun plus(a: Byte) {
        mas = mas.plus(a)
    }
}