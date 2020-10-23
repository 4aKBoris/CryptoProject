package com.example.cryptoproject.Function

import kotlin.experimental.xor
import kotlin.random.Random

class MetaDataInput(
    arr: ByteArray,
    password: String,
    hash_alggoritm: String,
    hash_count: Int,
    cipher_algoritm: String,
    cipher_cbc: String,
    cipher_padding: String,
    cipher_count: Int,
    iv: ByteArray,
    provider: Boolean,
    keysize: Int
) :
    MetaData(password) {

    private var arr: ByteArray
    private val hash_alggoritm: String
    private val hash_count: Int
    private val password: String
    private val cipher_algoritm: String
    private val cipher_cbc: String
    private val cipher_padding: String
    private val cipher_count: Int
    private var salt: ByteArray? = null
    private val iv: ByteArray
    private val provider: Boolean
    private val keysize: Int
    private var zeroByte = 0

    init {
        this.arr = arr
        this.password = password
        this.hash_alggoritm = hash_alggoritm
        this.hash_count = hash_count
        this.cipher_algoritm = cipher_algoritm
        this.cipher_cbc = cipher_cbc
        this.cipher_padding = cipher_padding
        this.cipher_count = cipher_count
        this.iv = iv
        this.provider = provider
        this.keysize = keysize
    }

    fun setZeroByte(zeroByte : Int) {
        this.zeroByte = zeroByte
    }

    fun setSalt(salt: ByteArray) {
        this.salt = salt
    }

    private var meta = ArrayList<Byte>()

    private val rnd = Random

    fun metaData(): ByteArray {
        meta.add(if (provider) random(true) else random(false))
        meta.add(hashfunInput[hash_alggoritm]!! xor rndSeek.nextInt().toByte())
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
        meta.add(cryptofunInput[cipher_algoritm]!! xor rndSeek.nextInt().toByte())
        meta.add(cipher_count.toByte() xor rndSeek.nextInt().toByte())
        if (cipher_algoritm !in cipherStream) {
            meta.add(cryptoCBCInput[cipher_cbc]!! xor rndSeek.nextInt().toByte())
            meta.add(cryptoPaddingInput[cipher_padding]!! xor rndSeek.nextInt().toByte())
            iv.forEach { meta.add(it) }
        }
        meta.add(keysize.toByte() xor rndSeek.nextInt().toByte())
        meta.add(zeroByte.toByte() xor rndSeek.nextInt().toByte())
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