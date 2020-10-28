package com.example.cryptoproject

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cryptoproject.Function.Cipher
import com.example.cryptoproject.Function.SetOfAlg
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class TestCipher {

    val password = "123456789qwertyQWERTY"
    val rnd = Random
    val set = SetOfAlg()
    val mas = rnd.nextBytes(523)

    @Test
    fun BCStreamCipher() {
        val t3 = "NoPadding"
        val t2 = "CBC"
        set.hash_alg_bc.forEach { t ->
            set.cipher_alg_bc.forEach { t1 ->
                if (t1 in set.cipherStream) {
                    println("$t $t1")
                    val list = set.keySize[t1]
                    for (i in list!![0]..list[2] step (list[1])) {
                        val cipher = Cipher(
                            mas,
                            password,
                            t,
                            3,
                            t1,
                            1,
                            true,
                            t2,
                            t3,
                            true,
                            i
                        )
                        val mas1 = cipher.Encrypt()
                        val cipher1 =
                            Cipher(mas1, password)
                        val clear = cipher1.Decrypt()
                        clear.forEachIndexed { index, byte ->
                            assertEquals(
                                byte,
                                mas[index]
                            )
                        }

                    }
                }
            }
        }
    }

    @Test
    fun StreamCipher() {
        val t3 = "NoPadding"
        val t2 = "CBC"
        set.hash_alg_default.forEach { t ->
            set.cipher_alg_default.forEach { t1 ->
                if (t1 in set.cipherStream) {
                    println("$t $t1")
                    val list = set.keySize[t1]
                    for (i in list!![0]..list[2] step (list[1])) {
                        val cipher = Cipher(
                            mas,
                            password,
                            t,
                            3,
                            t1,
                            1,
                            true,
                            t2,
                            t3,
                            false,
                            i
                        )
                        val mas1 = cipher.Encrypt()
                        val cipher1 =
                            Cipher(mas1, password)
                        val clear = cipher1.Decrypt()
                        clear.forEachIndexed { index, byte ->
                            assertEquals(
                                byte,
                                mas[index]
                            )
                        }


                    }

                }
            }
        }
    }

    @Test
    fun BlockCipher() {
        set.hash_alg_default.forEach { t ->
            set.cipher_alg_default.forEach { t1 ->
                if (t1 !in set.cipherStream) {
                    set.cipher_bcm_default.forEach { k1 ->
                        var t2 = k1
                        if (t1 !in set.cipher128 && t2 in set.cbc128) t2 = "CBC"
                        if (t1 !in set.cipher64 && t2 == "GOFB") t2 = "CBC"
                        set.cipher_padding_default.forEach { k2 ->
                            var t3 = k2
                            if ((t2 != "ECB" && t2 != "CBC") && t3 == "withCTS") t3 = "NoPadding"
                            if (t2 in set.AEAD && t3 != "NoPadding") t3 = "NoPadding"
                            val list = set.keySize[t1]
                            println("$t $t1/$k1/$k2")
                            for (i in list!![0]..list[2] step (list[1])) {
                                val cipher = Cipher(
                                    mas,
                                    password,
                                    t,
                                    10,
                                    t1,
                                    3,
                                    true,
                                    t2,
                                    t3,
                                    false,
                                    i
                                )
                                val arr = cipher.Encrypt()
                                val cipher1 =
                                    Cipher(arr, password)
                                val clear = cipher1.Decrypt()
                                clear.forEachIndexed { index, byte ->
                                    assertEquals(
                                        byte,
                                        mas[index]
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    @Test
    fun BCBlockCipher() {
        set.cipher_alg_bc.forEach { t1 ->
                if (t1 !in set.cipherStream) {
                    set.cipher_bcm_bc.forEach { k1 ->
                        var t2 = k1
                        if (t1 !in set.cipher128 && t2 in set.cbc128) t2 = "CBC"
                        if (t1 !in set.cipher64 && t2 == "GOFB") t2 = "CBC"
                        set.cipher_padding_bc.forEach { k2 ->
                            var t3 = k2
                            if ((t2 != "ECB" && t2 != "CBC") && t3 == "withCTS") t3 = "NoPadding"
                            if (t2 in set.AEAD && t3 != "NoPadding") t3 = "NoPadding"
                            val list = set.keySize[t1]
                            println("$t1/$k1/$k2")
                            for (i in 0..2 step (2)) {
                                val cipher = Cipher(
                                    mas,
                                    password,
                                    "SHA-256",
                                    2,
                                    t1,
                                    1,
                                    false,
                                    t2,
                                    t3,
                                    true,
                                    list!![i]
                                )
                                val arr = cipher.Encrypt()
                                val cipher1 =
                                    Cipher(arr, password)
                                val clear = cipher1.Decrypt()
                                clear.forEachIndexed { index, byte ->
                                    assertEquals(
                                        byte,
                                        mas[index]
                                    )
                                }
                            }

                        }
                    }
            }
        }
    }

    @Test
    fun BlockCipherTest() {
        set.hash_alg_bc.forEach { t ->
            val t1 = "GOST3412-2015"
            if (t1 !in set.cipherStream) {
                set.cipher_bcm_bc.forEach { k1 ->
                    var t2 = k1
                    if (t1 !in set.cipher128 && t2 in set.cbc128) t2 = "CBC"
                    if (t1 !in set.cipher64 && t2 == "GOFB") t2 = "CBC"
                    if (t1 == "GOST3412-2015" && t2 == "CTR") t2 = "CBC"
                    set.cipher_padding_bc.forEach { k2 ->
                        var t3 = k2
                        if ((t2 != "ECB" && t2 != "CBC") && t3 == "withCTS") t3 = "NoPadding"
                        if (t2 in set.AEAD && t3 != "NoPadding") t3 = "NoPadding"
                        val list = set.keySize[t1]
                        println("$t $t1/$k1/$k2")
                        for (i in 0..2 step (2)) {
                            val cipher = Cipher(
                                mas,
                                password,
                                t,
                                2,
                                t1,
                                1,
                                false,
                                t2,
                                t3,
                                true,
                                list!![i]
                            )
                            val arr = cipher.Encrypt()
                            val cipher1 =
                                Cipher(arr, password)
                            val clear = cipher1.Decrypt()
                            clear.forEachIndexed { index, byte ->
                                assertEquals(
                                    byte,
                                    mas[index]
                                )
                            }
                        }

                    }

                }
            }
        }
    }
}