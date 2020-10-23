package com.example.cryptoproject.Function

import android.content.SharedPreferences
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import javax.crypto.Cipher
import kotlin.properties.Delegates

class Cipher(
    arr: ByteArray,
    password: String
) : Algoritms() {

    private var arr: ByteArray
    private val password: String
    private lateinit var hash_alg: String
    private var hash_count by Delegates.notNull<Int>()
    private var saltflag by Delegates.notNull<Boolean>()
    private lateinit var cipher_alg: String
    private var cipher_count by Delegates.notNull<Int>()
    private lateinit var cbc: String
    private lateinit var padding: String
    private var provider by Delegates.notNull<Boolean>()
    private var salt = ByteArray(16)

    init {
        this.arr = arr
        this.password = password
    }

    constructor(arr: ByteArray, password: String, sp: SharedPreferences) : this(arr, password) {
        this.hash_alg = spHashAlg(sp)
        this.hash_count = spHashCount(sp)
        this.cipher_alg = spCipherAlg(sp)
        this.cipher_count = spCipherCount(sp)
        this.saltflag = spSalt(sp)
        this.cbc = spCBC(sp)
        this.padding = spPadding(sp)
        this.provider = spProvider(sp)
    }

    private val rnd = SecureRandom()

    private var iv = ByteArray(24)

    fun Encrypt(): ByteArray {
        val hashFun = Hash(password, hash_alg, hash_count, saltflag, provider)
        val hash = hashFun.Hash()
        var arr_cipher = arr
        rnd.nextBytes(iv)
        for (k in 1..cipher_count) arr_cipher = SingleCrypt(hash, arr_cipher, Cipher.ENCRYPT_MODE)
        val meta = MetaDataInput(arr_cipher,password,hash_alg,hash_count,cipher_alg,cbc,padding,cipher_count,iv,provider)
        if (saltflag) meta.setSalt(hashFun.getSalt())
        return meta.metaData()
    }

    private fun SingleCrypt(hash: ByteArray, arr: ByteArray, mode: Int): ByteArray {
        var cipher = if (cipher_alg in cipherStream) {
            if (provider) Cipher.getInstance(cipher_alg, BouncyCastleProvider())
            else Cipher.getInstance(cipher_alg)
        } else {
            if (provider) Cipher.getInstance("$cipher_alg/$cbc/$padding", BouncyCastleProvider())
            else Cipher.getInstance("$cipher_alg/$cbc/$padding")
        }
        return cipherInit(
            cipher_alg,
            cipher,
            keyGenerator(hash, cipher_alg),
            mode,
            iv,
            cbc
        ).doFinal(arr)
    }

    fun Decrypt(): ByteArray {
        var cipher_text = getData()
        val hashFun = Hash(password, hash_alg, hash_count, saltflag, provider)
        if (saltflag) hashFun.setSalt(salt)
        val hash = hashFun.Hash()
        for (k in 1..cipher_count) cipher_text = SingleCrypt(hash, cipher_text, Cipher.DECRYPT_MODE)
        return cipher_text
    }

    private fun getData(): ByteArray {
        val meta = MetaDataOutput(arr, password)
        val mas = meta.metaData()
        hash_alg = meta.getHashAlg()
        if (hash_alg !in cipherStream) iv = meta.getIV()
        hash_count = meta.getHashCount()
        cipher_alg = meta.getCipherAlg()
        cipher_count = meta.getCipherCount()
        saltflag = meta.getFlagSalt()
        if (saltflag) salt = meta.getSalt()!!
        cbc = meta.getCBC()
        padding = meta.getPadding()
        provider = meta.getProvader()
        return mas
    }
}