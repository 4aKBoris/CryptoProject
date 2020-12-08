@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import com.example.cryptoproject.DataObject.MetaData
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
open class Hash(
    private val meta: MetaData,
) {

    private val rnd = SecureRandom()

    fun Hash(): ByteArray {
        meta.run {
            val hash = password.toByteArray(StandardCharsets.UTF_8)
            if (flag_salt && salt == null) {
                salt = ByteArray(16)
                rnd.nextBytes(salt)
            }
            return HashFunction(hash)
        }
    }

    fun getSalt(): ByteArray {
        return meta.salt!!
    }

    fun setSalt(salt : ByteArray) {
        meta.salt = salt
    }

    private fun HashFunction(hash: ByteArray): ByteArray {
        var hs = hash
        meta.run {
            for (i in 1 until hash_count) {
                val digest = try {
                    MessageDigest.getInstance(hash_alg)
                } catch (e: Exception) {
                    MessageDigest.getInstance(hash_alg, BouncyCastleProvider())
                }
                if (flag_salt) digest.update(salt)
                hs = digest.digest(hs)
            }
        }
        return hs
    }
}