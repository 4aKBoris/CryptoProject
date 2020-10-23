package com.example.cryptoproject

import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        /*val setalg = setOf(
            "AES",
            "Blowfish",
            "Camellia",
            "CAST5",
            "CAST6",
            "DES",
            "DESede",
            "GOST28147",
            "IDEA",
            "Noekeon",
            "Rijndael",
            "SEED",
            "Shacal2",
            "Serpent",
            "Skipjack",
            "SM4",
            "TEA",
            "XTEA",
            "Twofish",
            "RC2",
            "RC4",
            "RC5",
            "RC6",
            "HC128",
            "HC256",
            "ChaCha",
            "Salsa20",
            "XSalsa20",
            "VMPC",
            "Grainv1",
            "Grain128"
        )
        val sethash = setOf("MD2", "MD4", "MD5", "SHA-1", "SHA-256", "SHA-512")

        sethash.forEach { t ->
            val hash = Hash("12345678910", t, 10, true, true)
            val hs = hash.Hash()
            setalg.forEach { it ->
                var rnd = Random
                val text = rnd.nextBytes(958)
                val en = Encrypt(it, text, hs, 5)
                val cr = en.Encrypt()
                val iv = en.getIV()
                val dec = Decrypt(it, cr, hs, 5, iv)
                val clear = dec.Decrypt()
            }
        }*/


    }

    @Test
    fun TestFun() {
    }
}
