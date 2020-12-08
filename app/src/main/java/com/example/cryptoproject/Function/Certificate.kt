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
    private val common_name: String,
    private val organizational_unit: String,
    private val organization_name: String,
    private val locality: String,
    private val state: String,
    private val country: String,
    private val ANDROID_ID: String,
    private val password: String
) {

    @SuppressLint("SdCardPath")
    private val Path = "/data/data/com.example.cryptoproject/my_keystore.ks"

    fun CreateCertificate() {
        val s = SetOfAlg()
        val keyStore = KeyStore.getInstance("PKCS12")
        keyStore.load(null, password.toCharArray())
        s.sign.filter { it != "Не использовать" }.forEach {
            Security.addProvider(BouncyCastleProvider())
            val keyPairGenerator = KeyPairGenerator.getInstance(Alg(it))
            val keyPair = keyPairGenerator.generateKeyPair()
            val gen = X509V3CertificateGenerator()
            val server_common_name = X500Principal("CN=${common_name}")
            val server_organizational_unit = X500Principal("OU=${organizational_unit}")
            val server_organization_name = X500Principal("O=${organization_name}")
            val server_locality = X500Principal("L=${locality}")
            val server_state = X500Principal("ST=${state}")
            val server_country = X500Principal("C=${country}")
            gen.setSerialNumber(BigInteger(ANDROID_ID.toByteArray()))
            val after = Date(2030, 1, 1, 0, 0, 0)
            val before = Date()
            gen.setIssuerDN(server_common_name)
            gen.setNotBefore(after)
            gen.setNotAfter(before)
            gen.setSubjectDN(server_common_name)
            gen.setSubjectDN(server_organizational_unit)
            gen.setSubjectDN(server_organization_name)
            gen.setSubjectDN(server_locality)
            gen.setSubjectDN(server_state)
            gen.setSubjectDN(server_country)
            gen.setPublicKey(keyPair.public)
            gen.setSignatureAlgorithm(it)
            val myCert = gen.generate(keyPair.private)
            keyStore.setCertificateEntry(it, myCert)
            keyStore.setKeyEntry(it, keyPair.private, null, arrayOf(myCert));
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