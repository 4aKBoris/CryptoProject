@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import kotlin.experimental.xor
import kotlin.random.Random

class MetaDataInput(
    private var arr: ByteArray,
    password: String,
    private val hash_alggoritm: String,
    private val hash_count: Int,
    private val cipher_algoritm: String,
    private val cipher_cbc: String,
    private val cipher_padding: String,
    private val cipher_count: Int,
    private val iv: ByteArray,
    private val provider: Boolean,
    private val keysize: Int
) :
    MetaData(password) {

    private var salt: ByteArray? = null
    private var zeroByte = 0

    fun setSalt(salt: ByteArray) {
        this.salt = salt
    }

    fun setZeroByte(zeroByte : Int) {
        this.zeroByte = zeroByte
    }

    private var meta = ArrayList<Byte>()

    private val rnd = Random

    fun metaData(): ByteArray {
        meta.add(if (provider) random(true) else random(false))
        meta.add(hashfunInput.getValue(hash_alggoritm) xor rndSeek.nextInt().toByte())
        if (hash_count > 127) {
            meta.add(random(true))
            meta.add((hash_count / 128).toByte())
            meta.add((hash_count % 128).toByte())
        } else {
            meta.add(random(false))
            meta.add(hash_count.toByte())
            meta.add(rnd.nextInt().toByte())
        }
        if (salt != null) {
            meta.add(random(true))
            salt!!.forEach { meta.add(it) }
        } else meta.add(random(false))
        meta.run {
            add(cryptofunInput.getValue(cipher_algoritm) xor rndSeek.nextInt().toByte())
            add(cipher_count.toByte() xor rndSeek.nextInt().toByte())
        }
        if (cipher_algoritm !in cipherStream) {
            meta.run {
                add(cryptoCBCInput.getValue(cipher_cbc) xor rndSeek.nextInt().toByte())
                add(cryptoPaddingInput.getValue(cipher_padding) xor rndSeek.nextInt().toByte())
            }
        }
        iv.forEach { meta.add(it) }
        meta.run {
            add(keysize.toByte() xor rndSeek.nextInt().toByte())
            add(zeroByte.toByte() xor rndSeek.nextInt().toByte())
        }
        return meta.toByteArray().plus(arr)
    }

    private fun random(flag: Boolean): Byte {
        var fl = rnd.nextInt()
        while ((fl % 2 == 0) == flag) {
            fl = rnd.nextInt()
        }
        return fl.toByte()
    }
}