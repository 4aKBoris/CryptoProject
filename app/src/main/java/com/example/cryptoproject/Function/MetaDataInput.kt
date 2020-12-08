@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.annotation.SuppressLint
import com.example.cryptoproject.DataObject.MetaData
import com.example.cryptoproject.Сonstants.*
import java.security.*
import javax.crypto.Cipher.getInstance
import kotlin.random.Random

class MetaDataInput(
    private val meta : MetaData
) {

    private lateinit var publicKey: PublicKey
    private lateinit var password_key_store: String

    private val rnd = Random
    private var mas = arrayListOf<Byte>()

    fun setPublicKey(publicKey: PublicKey) {
        this.publicKey = publicKey
    }

    fun setPasswordKeyStore(password_key_store: String) {
        this.password_key_store = password_key_store
    }

    @SuppressLint("SdCardPath")
    fun metaData(): ByteArray {
        meta.run {
            mas.run {
                if (cipher_password) Encrypt()
                else add(random(false))
                add(hashAlg.indexOf(hash_alg).toByte())
                if (hash_count > 127) {
                    add(random(true))
                    add((hash_count / 128).toByte())
                    add((hash_count % 128).toByte())
                } else {
                    add(random(false))
                    add(hash_count.toByte())
                    add(rnd.nextInt().toByte())
                }
                if (flag_salt) {
                    add(random(true))
                    addAll(salt!!.toList())
                } else add(random(false))
                add(cipherAlg.indexOf(cipher_alg).toByte())
                add(cipher_count.toByte())
                if (cipher_alg !in cipherStream) {
                    add(cipherBcm.indexOf(bcm).toByte())
                    add(cipherPadding.indexOf(padding).toByte())
                }
                addAll(iv.toList())
                add(keysize.toByte())
                add(zeroByte.toByte())
            }
            array = mas.toByteArray().plus(array)
            if (signature != NotUse) {
                val sign = Signature(array, signature, password_key_store)
                array = sign.SignEncrypt()
            }
            return byteArrayOf(sign.indexOf(signature).toByte()).plus(array)
        }
    }

    private fun Encrypt() {
        mas.add(random(true))
        val cipher = getInstance(RSA)
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, publicKey)
        mas.addAll(cipher.doFinal(meta.password.toByteArray()).toList())
    }

    private fun random(flag: Boolean): Byte {
        var fl = rnd.nextInt()
        while ((fl % 2 == 0) == flag) {
            fl = rnd.nextInt()
        }
        return fl.toByte()
    }
}