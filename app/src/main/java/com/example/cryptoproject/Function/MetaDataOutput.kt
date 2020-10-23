package com.example.cryptoproject.Function

import kotlin.experimental.xor
import kotlin.math.abs

class MetaDataOutput(arr: ByteArray, password: String) :
    MetaData(password) {
    private val password: String
    private var arr: ByteArray
    private lateinit var hash_alg: String
    private var hash_count: Int = 0
    private var salt: ByteArray? = null
    private lateinit var cipher_alg: String
    private var cipher_count = 0
    private var iv = ByteArray(24)
    private var flagSalt: Boolean = false
    private var provider = false
    private var cbc = ""
    private var padding = ""
    private var keysize = 32
    private var zeroByte = 0


    init {
        this.arr = arr
        this.password = password
    }

    fun getCipherAlg(): String {
        return cipher_alg
    }

    fun getHashAlg(): String {
        return hash_alg
    }

    fun getHashCount(): Int {
        return hash_count
    }

    fun getCipherCount(): Int {
        return cipher_count
    }

    fun getIV(): ByteArray {
        return iv
    }

    fun getFlagSalt(): Boolean {
        return flagSalt
    }

    fun getSalt(): ByteArray? {
        return salt
    }

    fun getProvader() : Boolean {
        return provider
    }

    fun getCBC() : String {
        return cbc
    }

    fun getPadding() : String {
        return padding
    }

    fun getKeySize() : Int {
        return keysize
    }

    fun getZeroByte() : Int {
        return zeroByte
    }

    fun metaData(): ByteArray {
        val mas = arr.toMutableList()
        provider = mas.removeAt(0) % 2 != 0
        hash_alg=  hashfunOutput[mas.removeAt(0) xor rndSeek.nextInt().toByte()]!!
        if (mas.removeAt(0) % 2 == 0) {
            hash_count = mas.removeAt(0).toInt()
            mas.removeAt(0)
        } else {
            val hLeft = mas.removeAt(0)
            val hRight = mas.removeAt(0)
            hash_count = hRight + hLeft * 128
        }
        if (mas.removeAt(0) % 2 != 0) {
            flagSalt = true
            salt = ByteArray(16)
            for (i in 0 until 16) salt!![i] = mas.removeAt(0)
        }
        cipher_alg = cryptofunOutput[mas.removeAt(0) xor rndSeek.nextInt().toByte()]!!
        cipher_count = (mas.removeAt(0) xor rndSeek.nextInt().toByte()).toInt()
        if (cipher_alg !in cipherStream) {
            cbc = cryptoCBCOutput[mas.removeAt(0) xor rndSeek.nextInt().toByte()]!!
            padding = cryptoPaddingOutput[mas.removeAt(0) xor rndSeek.nextInt().toByte()]!!
            for (i in 0 until 24) iv[i] = mas.removeAt(0)
        }
        keysize = abs((mas.removeAt(0) xor rndSeek.nextInt().toByte()).toInt())
        zeroByte = (mas.removeAt(0) xor rndSeek.nextInt().toByte()).toInt()
        return mas.toByteArray()
    }
}