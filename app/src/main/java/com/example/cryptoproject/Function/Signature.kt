@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.annotation.SuppressLint
import com.example.cryptoproject.Сonstants.CertificatesPath
import com.example.cryptoproject.Сonstants.X509
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.FileInputStream
import java.security.*
import java.security.Signature
import java.security.cert.CertificateFactory

class Signature(
    private val arr: ByteArray,
    private val signature_algorithm: String,
    private val str: String,
) {

    private val password = str.toCharArray()

    @SuppressLint("SdCardPath")
    fun SignEncrypt(): ByteArray {
        val keyStoreData = FileInputStream(PATH_KEY_STORE)
        val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
        keyStore.load(keyStoreData, password)
        val entryPassword = KeyStore.PasswordProtection(password)
        val privateKeyEntry =
            keyStore.getEntry(signature_algorithm, entryPassword) as KeyStore.PrivateKeyEntry
        val sign = SignEnc(privateKeyEntry.privateKey)
        return byteArrayOf((sign.size - 129).toByte()).plus(sign.plus(arr))
    }

    private fun SignEnc(private_key: PrivateKey): ByteArray {
        val signature = Signature.getInstance(signature_algorithm)
        signature.initSign(private_key, secureRandom)
        signature.update(arr)
        return signature.sign()
    }

    @SuppressLint("SdCardPath")
    fun SignDecrypt(): Boolean {
        val certificateFactory = CertificateFactory.getInstance(X509)
        val certificateInputStream = FileInputStream(str)
        val certificate = certificateFactory.generateCertificate(certificateInputStream)
        return SignDec(arr.copyOfRange(arr[1].toInt() + c130, arr.size),
            arr.copyOfRange(c2, arr[1].toInt() + c130),
            certificate.publicKey)
    }

    private fun SignDec(mas: ByteArray, sign: ByteArray, public_key: PublicKey): Boolean {
        val signature = Signature.getInstance(signature_algorithm)
        signature.initVerify(public_key)
        signature.update(mas)
        return signature.verify(sign)
    }

    companion object {
        @SuppressLint("SdCardPath")
        private const val PATH_KEY_STORE = "/data/data/com.example.cryptoproject/my_keystore.ks"
        private const val KEY_STORE_ALGORITHM = "PKCS12"
        private const val c130 = 131
        private const val c2 = 2
        private val secureRandom = SecureRandom()
    }
}