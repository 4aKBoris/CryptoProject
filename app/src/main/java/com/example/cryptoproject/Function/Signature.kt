@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.annotation.SuppressLint
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.FileInputStream
import java.security.*
import java.security.Signature

class Signature(
    private val arr: ByteArray,
    private val signature_algorithm: String,
    password_key_store: String,
) {

    private val password = password_key_store.toCharArray()

    @SuppressLint("SdCardPath")
    fun SignEncrypt(): ByteArray {
        val keyStoreData = FileInputStream(PATH_KEY_STORE)
        val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
        keyStore.load(keyStoreData, password)
        val entryPassword = KeyStore.PasswordProtection(password)
        val privateKeyEntry =
            keyStore.getEntry(signature_algorithm, entryPassword) as KeyStore.PrivateKeyEntry
        return SignEnc(privateKeyEntry.privateKey).plus(arr)
    }

    private fun SignEnc(private_key: PrivateKey): ByteArray {
        val signature = try {
            Signature.getInstance(signature_algorithm)
        } catch (e: Exception) {
            Signature.getInstance(signature_algorithm, BouncyCastleProvider())
        }
        signature.initSign(private_key, secureRandom)
        signature.update(arr)
        return signature.sign()
    }

    @SuppressLint("SdCardPath")
    fun SignDecrypt(): Boolean {
        val keyStoreData = FileInputStream(PATH_KEY_STORE)
        val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
        keyStore.load(keyStoreData, password)
        val certificate = keyStore.getCertificate(signature_algorithm)
        return SignDec(arr.copyOfRange(c257, arr.size),
            arr.copyOfRange(c1, c257),
            certificate.publicKey)
    }

    private fun SignDec(mas: ByteArray, sign: ByteArray, public_key: PublicKey): Boolean {
        val signature = try {
            Signature.getInstance(signature_algorithm)
        } catch (e: Exception) {
            Signature.getInstance(signature_algorithm, BouncyCastleProvider())
        }
        signature.initVerify(public_key)
        signature.update(mas)
        return signature.verify(sign)
    }

    companion object {
        @SuppressLint("SdCardPath")
        private const val PATH_KEY_STORE = "/data/data/com.example.cryptoproject/my_keystore.ks"
        private const val KEY_STORE_ALGORITHM = "PKCS12"
        private const val c257 = 257
        private const val c1 = 1
        private val secureRandom = SecureRandom()
    }
}