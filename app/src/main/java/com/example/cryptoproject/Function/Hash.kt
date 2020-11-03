@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
open class Hash(
    private val password: String,
    private val hash_alg: String,
    private val hash_count: Int,
    private val saltflag: Boolean,
    private var provider: Boolean
) {

    private var salt: ByteArray? = null

    private val rnd = SecureRandom()

    fun Hash(): ByteArray {
        val hash = password.toByteArray(StandardCharsets.UTF_8)
        if (saltflag && salt == null) {
            salt = ByteArray(16)
            rnd.nextBytes(salt)
        }
        return HashFunction(hash)
    }

    fun getSalt(): ByteArray {
        return salt!!
    }

    fun setSalt(salt : ByteArray) {
        this.salt = salt
    }

    private fun HashFunction(hash: ByteArray): ByteArray {
        var hs = hash
        for (i in 1 until hash_count) {
            val digest = if (provider) MessageDigest.getInstance(hash_alg, BouncyCastleProvider())
            else MessageDigest.getInstance(hash_alg)
            if (saltflag) digest.update(salt)
            hs = digest.digest(hs)
        }
        return hs
    }
}