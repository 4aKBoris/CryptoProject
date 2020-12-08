@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import com.example.cryptoproject.DataObject.MetaData
import com.example.cryptoproject.Сonstants.*
import java.io.FileInputStream
import java.security.KeyStore
import javax.crypto.Cipher
import kotlin.math.abs

class MetaDataOutput(private var arr: ByteArray) {

    private lateinit var password_key_store: CharArray

    private val meta = MetaData

    fun setPasswordKeyStore(password_key_store: String) {
        this.password_key_store = password_key_store.toCharArray()
    }

    fun setPassword(password: String) {
        meta.password = password
    }

    fun metaData(): MetaData {
        meta.run {
            var mas = arr.toMutableList()
            mas.run {
                if (removeFirst() % 2 != 0) mas = Decrypt(mas)
                hash_alg = hashAlg[removeFirst().toInt()]
                if (removeFirst() % 2 == 0) {
                    hash_count = removeFirst().toInt()
                    removeFirst()
                } else {
                    val hLeft = removeFirst()
                    val hRight = removeFirst()
                    hash_count = hRight + hLeft * 128
                }
                if (removeFirst() % 2 != 0) {
                    flag_salt = true
                    salt = ByteArray(16)
                    for (i in 0 until 16) salt!![i] = removeFirst()
                }
                cipher_alg = cipherAlg[removeFirst().toInt()]
                cipher_count = removeFirst().toInt()
                if (cipher_alg !in cipherStream) {
                    bcm = cipherBcm[removeFirst().toInt()]
                    padding = cipherPadding[removeFirst().toInt()]
                }
                for (i in 0 until BlockSize) iv[i] = removeFirst()
                keysize = abs(removeFirst().toInt())
                zeroByte = abs(removeFirst().toInt())
            }
            array = mas.toByteArray()
            return meta
        }
    }

    private fun Decrypt(mas: MutableList<Byte>): MutableList<Byte> {
        meta.cipher_password = true
        val pass = mutableListOf<Byte>()
        for (i in 1..256) pass.add(mas.removeFirst())
        val cipher = Cipher.getInstance(RSA)
        val keyStoreData = FileInputStream(PATH_KEY_STORE)
        val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
        keyStore.load(keyStoreData, password_key_store)
        val entryPassword = KeyStore.PasswordProtection(password_key_store)
        val privateKeyEntry =
            keyStore.getEntry(ALGORITHM, entryPassword) as KeyStore.PrivateKeyEntry
        cipher.init(Cipher.DECRYPT_MODE, privateKeyEntry.privateKey)
        meta.password = cipher.doFinal(pass.toByteArray()).toString(Charsets.UTF_8)
        return mas
    }
}