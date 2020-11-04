@file:Suppress("PackageName", "DEPRECATION")

package com.example.cryptoproject.Function

import android.annotation.SuppressLint
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.x509.X509V3CertificateGenerator
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Security
import java.util.*
import javax.security.auth.x500.X500Principal

@Suppress("DEPRECATION", "RedundantSemicolon")
class Certificate(
    private val organization: String,
    private val ANDROID_ID: String,
    private val password: String
) {

    @SuppressLint("SdCardPath")
    private val Path = "/data/data/com.example.cryptoproject/keystore.ks"

    fun CreateCertificate() {
        val s = SetOfAlg()
        val keyStore = KeyStore.getInstance("PKCS12")
        keyStore.load(null, password.toCharArray())
        s.cerf.forEach {
            Security.addProvider(BouncyCastleProvider())
            val keyPairGenerator = KeyPairGenerator.getInstance(Alg(it))
            val keyPair = keyPairGenerator.generateKeyPair()
            val gen = X509V3CertificateGenerator()
            val serverSubjectName = X500Principal("CN=${organization}")
            gen.setSerialNumber(BigInteger(ANDROID_ID.toByteArray()))
            val after = Date(2030, 1, 1, 0, 0, 0)
            val before = Date()
            gen.setIssuerDN(serverSubjectName)
            gen.setNotBefore(after)
            gen.setNotAfter(before)
            gen.setSubjectDN(serverSubjectName)
            gen.setPublicKey(keyPair.public)
            gen.setSignatureAlgorithm(it)
            val myCert = gen.generate(keyPair.private)
            keyStore.setCertificateEntry(it, myCert)
        }
        val keyStoreOutputStream = FileOutputStream(Path)
        keyStore.store(keyStoreOutputStream, password.toCharArray())
    }

    private fun Alg(sign_alg: String): String {
        return when {
            sign_alg.indexOf("EC") != -1 -> "EC"
            sign_alg.indexOf("with") == -1 -> sign_alg
            else -> sign_alg.substring(sign_alg.indexOf("with") + 4)
        }
    }
}