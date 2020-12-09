@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import android.content.SharedPreferences
import com.example.cryptoproject.DataObject.MetaData
import com.example.cryptoproject.Сonstants.*
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.FileInputStream
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.crypto.Cipher

@Suppress("DEPRECATION")
class Cipher(arr: ByteArray) : Algoritms() {

    private var meta = MetaData
    private var password_key_store = ""
    private lateinit var certificate_path: String

    private val rnd = SecureRandom()

    init {
        meta.array = arr
    }

    constructor(arr: ByteArray, password: String, sp: SharedPreferences) : this(arr) {
        meta.run {
            this.password = password
            hash_alg = spHashAlg(sp)
            hash_count = spHashCount(sp)
            cipher_alg = spCipherAlg(sp)
            cipher_count = spCipherCount(sp)
            flag_salt = spSalt(sp)
            bcm = spCBC(sp)
            padding = spPadding(sp)
            keysize = spKeySize(sp)
            signature = spSignature(sp)
            cipher_password = spCipherPassword(sp)
        }
    }

    fun setPasswordKeyStore(password_key_store: String) {
        this.password_key_store = password_key_store
    }

    fun setCertificatePath(certificate_path: String) {
        this.certificate_path = certificate_path
    }

    fun setPassword(password: String) {
        meta.password = password
    }

    fun Encrypt(): ByteArray {
        meta.run {
            val ost = array.size % BlockSize
            if (ost != 0) {
                zeroByte = BlockSize - ost
                array = array.plus(rnd.generateSeed(zeroByte))
            }
            val hashFun = Hash(meta)
            val hash = hashFun.Hash()
            rnd.nextBytes(iv)
            for (k in 1..cipher_count) array = SingleCrypt(hash, array, Cipher.ENCRYPT_MODE)
            if (flag_salt) salt = hashFun.getSalt()
            val meta_data = MetaDataInput(meta)
            if (signature != NotUse) meta_data.setPasswordKeyStore(password_key_store)
            if (cipher_password) {
                val certificateFactory = CertificateFactory.getInstance(X509)
                val certificateInputStream =
                    FileInputStream(CertificatesPath + certificate_path)
                val certificate = certificateFactory.generateCertificate(certificateInputStream)
                meta_data.setPublicKey(certificate.publicKey)
            }
            return meta_data.metaData()
        }
    }

    private fun SingleCrypt(hash: ByteArray, arr: ByteArray, mode: Int): ByteArray {
        meta.run {
            val cipher = if (meta.cipher_alg in cipherStream) {
                try {
                    Cipher.getInstance(cipher_alg)
                } catch (e: Exception) {
                    Cipher.getInstance(cipher_alg, BouncyCastleProvider())
                }
            } else {
                try {
                    Cipher.getInstance("$cipher_alg/$bcm/$padding")
                } catch (e: Exception) {
                    Cipher.getInstance("$cipher_alg/$bcm/$padding", BouncyCastleProvider())
                }
            }
            return cipherInit(
                cipher_alg,
                cipher,
                keyGenerator(hash, cipher_alg, keysize),
                mode,
                iv,
                bcm
            ).doFinal(arr)
        }
    }

    fun Decrypt(): ByteArray {
        meta = getData()
        meta.run {
            val hashFun = Hash(meta)
            if (flag_salt) hashFun.setSalt(salt!!)
            val hash = hashFun.Hash()
            for (k in 1..cipher_count) array =
                SingleCrypt(hash, array, Cipher.DECRYPT_MODE)
            if (zeroByte != 0) array = array.copyOf(array.size - zeroByte)
            return array
        }
    }

    private fun getData(): MetaData {
        meta.run {
            val meta_data = MetaDataOutput(meta)
            if (password_key_store != "") meta_data.setPasswordKeyStore(password_key_store)
            if (password != "") meta_data.setPassword(password)
            return meta_data.metaData()
        }
    }
}