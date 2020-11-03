package com.example.cryptoproject

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Test
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("TestFunctionName")
class ExampleUnitTest {

    @Test
    fun TestFun() {
        //val rnd = Random
        //val k = MessageDigest.getInstance("1.2.804.2.1.1.1.1.2.2.1", BouncyCastleProvider())
        //val t = BCrypt.generate(byteArrayOf(1, 2, 3, 4, 5), byteArrayOf(1, 2, 3), 1)
        var i = 1
        BouncyCastleProvider().services.forEach{
            if (it.type == "Cipher")
            {
                try {
                    //val k = javax.crypto.Cipher.getInstance(it.algorithm, BouncyCastleProvider())
                    /*val key = SecretKeySpec(rnd.nextBytes(64), it.algorithm)
                    k.init(
                        javax.crypto.Cipher.ENCRYPT_MODE,
                        key,
                        IvParameterSpec(rnd.nextBytes(64))
                    )
                    var flag = true*/
                    /*SetOfAlg().cipher_alg_bc.forEach {t ->
                        if (t.toLowerCase() == it.algorithm.toLowerCase()) flag = false
                    }*/
                    /*if (flag) */println("${i++}. ${it.algorithm}")
                }
                catch (e : Exception) {
                    //println(e.message)
                }
            }
        }
    }

    @Test
    fun Cipher() {
        val rnd = Random
        val k =
            javax.crypto.Cipher.getInstance("GOST3412-2015/CTR/NoPadding", BouncyCastleProvider())
        val key = SecretKeySpec(rnd.nextBytes(32), "GOST3412-2015")
        k.init(
            javax.crypto.Cipher.ENCRYPT_MODE,
            key,
            IvParameterSpec(rnd.nextBytes(16))
        )
    }

    @Test
    fun KeySize() {
        val k = javax.crypto.Cipher.getInstance("RC4", BouncyCastleProvider())
        val rnd = Random
        for (i in 5..1000000) {
            val key = SecretKeySpec(rnd.nextBytes(i), "RC4")
            k.init(
                javax.crypto.Cipher.ENCRYPT_MODE, key
            )
            println(i)
        }
    }
}