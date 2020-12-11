@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import com.example.cryptoproject.DataObject.MetaData
import com.example.cryptoproject.Сonstants.*
import java.io.FileInputStream
import java.security.KeyStore
import javax.crypto.Cipher
import kotlin.math.abs

class MetaDataOutput(private var meta: MetaData) {

    private var i = 0

    private lateinit var password_key_store: CharArray

    fun setPasswordKeyStore(password_key_store: String) {
        this.password_key_store = password_key_store.toCharArray()
    }

    fun setPassword(password: String) {
        meta.password = password
    }

    fun metaData(): MetaData {
        meta.run {
            if (array[i++] % 2 != 0) Decrypt()
            hash_alg = hashAlg[array[i++].toInt()]
            if (array[i++] % 2 == 0) {
                hash_count = array[i++].toInt()
                array[i++]
            } else {
                val hLeft = array[i++]
                val hRight = array[i++]
                hash_count = hRight + hLeft * BlockSize
            }
            if (array[i++] % 2 != 0) {
                flag_salt = true
                salt = array.copyOfRange(i, i + n16)
                i += n16
            }
            cipher_alg = cipherAlg[array[i++].toInt()]
            cipher_count = array[i++].toInt()
            if (cipher_alg !in cipherStream) {
                bcm = cipherBcm[array[i++].toInt()]
                padding = cipherPadding[array[i++].toInt()]
            }
            iv = array.copyOfRange(i, i + BlockSize)
            i += BlockSize
            keysize = abs(array[i++].toInt())
            zeroByte = abs(array[i++].toInt())
            array = array.copyOfRange(i, array.size)
        }
        return meta
    }

    private fun Decrypt() {
        meta.run {
            cipher_password = true
            i += n256
            val pass = array.copyOfRange(i - n256, i)
            if (password == "") {
                val cipher = Cipher.getInstance(RSA)
                val keyStoreData = FileInputStream(PATH_KEY_STORE)
                val keyStore = KeyStore.getInstance(KEY_STORE_ALGORITHM)
                keyStore.load(keyStoreData, password_key_store)
                val entryPassword = KeyStore.PasswordProtection(password_key_store)
                val privateKeyEntry =
                    keyStore.getEntry(ALGORITHM, entryPassword) as KeyStore.PrivateKeyEntry
                cipher.init(Cipher.DECRYPT_MODE, privateKeyEntry.privateKey)
                password = cipher.doFinal(pass).toString(Charsets.UTF_8)
            }
        }
    }

    companion object {
        private const val n16 = 16
        private const val n256 = 256
    }
}