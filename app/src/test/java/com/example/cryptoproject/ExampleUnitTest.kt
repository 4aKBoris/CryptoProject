package com.example.cryptoproject

import org.bouncycastle.jcajce.provider.symmetric.GOST3412_2015
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Test
import java.security.InvalidKeyException
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        /*val hash = Hash("123456", "SHA-256", 10, false)
        val hs = hash.Hash()
        //hs.forEach { print("$it ") }
        val arr = arrayListOf<ByteArray>(
            arrayListOf<Byte>(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10
            ).toByteArray()
        )
        val En = Encrypt("AES", arr, hs, 1)
        val cr = En.Encrypt()
        val iv = En.getIV()
        cr.forEach { t -> t.forEach { print("$it ") } }
        val Dec = Decrypt("AES", cr, hs, 1, iv)
        println()
        //Dec.getIV().forEach { print("$it ") }
        val text = Dec.Decrypt()
        text.forEach { t -> t.forEach { print("$it ") } }

        assertEquals(false, false)*/
    }

    @Test
    fun test1() {
        /*val alg = "RC5"
        Security.addProvider(BouncyCastleProvider())
        val hash = Hash("123456", "MD5", 10, false)
        var hs = hash.Hash().copyOf(24)
        val rnd = Random
        val iv = rnd.nextBytes(8)
        val cipher1 = Cipher.getInstance(alg)
        //hs = hs.copyOfRange(0, 7)
        cipher1.init(Cipher.ENCRYPT_MODE, SecretKeySpec(hs, alg))

        val text = arrayListOf<Byte>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12).toByteArray()

        text.forEach { print("$it ") }
        println()

        val cipher = cipher1.doFinal(text)
        cipher.forEach { print("$it ") }
        println()

        val cipher2 = Cipher.getInstance(alg)
        cipher2.init(Cipher.DECRYPT_MODE, SecretKeySpec(hs, 0, hs.size, alg))

        val cleartext = cipher2.doFinal(cipher)
        cleartext.forEach { print("$it ") }
        println()*/
    }

    @Test
    fun hash() {
        /*val hash = Hash("123456", "MD5", 10, false)
        var hs = hash.Hash()
        hs = hs.copyOf(10)
        val rnd = Random
        val iv = rnd.nextBytes(16)
        val cipher1 = Cipher.getInstance("Grainv1")
        println(cipher1.blockSize)
        cipher1.init(Cipher.ENCRYPT_MODE, SecretKeySpec(hs, "Grainv1"))*/
    }

    @Test
    fun Crypto() {
        val setalg = setOf(
            "AES",
            "Blowfish",
            "Camellia",
            "CAST5",
            "CAST6",
            "DES",
            "DESede",
            "TripleDES",
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
        /*val sethash = setOf("MD2", "MD4", "MD5", "SHA-1", "SHA-256", "SHA-512")

        sethash.forEach { t ->
            val hash = Hash("12345678910", t, 10, true, true)
            val hs = hash.Hash()
            setalg.forEach { it ->
                println(it)
                println(t)
                var rnd = Random
                val text = rnd.nextBytes(958)
                text.forEach { print("$it ") }
                println()
                val en = Encrypt(it, text, hs, 5)
                val cr = en.Encrypt()
                cr.forEach { print("$it ") }
                println()
                val iv = en.getIV()
                val dec = Decrypt(it, cr, hs, 5, iv)
                val clear = dec.Decrypt()
                clear.forEach { print("$it ") }
                println()
                text.forEachIndexed { index, byte -> assertEquals(byte, clear[index]) }
            }
        }*/
    }

    @Test
            /*fun test2() {
                val hash = Hash("12345678910", "SHA-256", 10, true, true)
                val hs = hash.Hash()
                var rnd = Random
                val text = rnd.nextBytes(584)
                text.forEach { print("$it ") }
                println()
                val en = Encrypt("Random", text, hs, 50)
                val r = en.getRandom()
                val cr = en.Encrypt()
                cr.forEach { print("$it ") }
                println()
                val iv = en.getIV()
                val dec = Decrypt("Random", cr, hs, 50, iv)
                dec.setRandomCipher(r)
                val clear = dec.Decrypt()
                clear.forEach { print("$it ") }
                println()
                text.forEachIndexed { index, byte -> assertEquals(byte, clear[index]) }
            }*/
    fun testr() {
        Cipher.getInstance("AESWrap", BouncyCastleProvider())
    }


    @Test
    fun tdwas() {
        /*val setalg = setOf(
            "AES",
            "AESWrap",
            "ARIA",
            "ARIAWrap",
            //"Blowfish",
            "Camellia",
            "CamelliaWrap",
            "CAST5",
            "CAST6",
            "DES",
            "DESede",
            "DESedeWrap",
            "DSTU7624",
            "GCM",
            "GOST28147",
            "IDEA",
            "Noekeon",
            "Rijndael",
            "SEEDWrap",
            "SEED",
            "Shacal2",
            "Serpent",
            "Skipjack",
            "SM4",
            "TEA",
            "XTEA",
            "Twofish",
            "Threefish-256",
            "Threefish-512",
            "Threefish-1024",
            "RC2",
            "RC4",
            "RC5",
            "RC5-64",
            "RC6",
            "HC128",
            "HC256",
            "ChaCha",
            "Salsa20",
            "XSalsa20",
            "VMPC",
            "Grainv1",
            "Grain128"
        )*/

        val setalg = setOf(
            "AES",
            "Blowfish",
            "CAST5",
            "DES",
            "DESede",
            "GOST28147",
            "IDEA",
            "RC2",
            "RC5",
            "Skipjack",
            "TEA",
            "XTEA"
        )

        val setStream = setOf(
            "AESWrap",
            "ARIAWrap",
            "CamelliaWrap",
            "DESedeWrap",
            "DSTU7624Wrap",
            "SEEDWrap",
            "RC4",
            "HC128",
            "HC256",
            "ChaCha",
            "Salsa20",
            "XSalsa20",
            "VMPC",
            "Grainv1",
            "Grain128"
        )

        val cbc = setOf(
            "ECB",
            "CBC",
            "OFB",
            "CFB",
            "CTR",
            "OpenPGPCFB",
            "CTS",
            "GOFB",
            "GCFB",
            "CCM",
            "EAX",
            "GCM",
            "OCB"
        )

        val padding = setOf(
            "NoPadding",
            "PKCS5Padding",
            "PKCS7Padding",
            "ISO10126Padding",
            "ISO10126-2Padding",
            "ISO7816-4Padding",
            "ISO9797-1Padding",
            "X923Padding",
            "X9.23Padding",
            "TBCPadding",
            "ZeroBytePadding",
            //"withCTS"
        )
        setalg.forEach { t ->
            cbc.forEach { k ->
                padding.forEach {
                    if ((k == "CCM" || k == "EAX" || k == "GCM" || k == "OCB") && t !in setStream) {
                        val cipher = Cipher.getInstance(
                            "$t/$k/NoPadding",
                            BouncyCastleProvider()
                        )
                    } else if (t !in setStream) {
                        val cipher = Cipher.getInstance(
                            "$t/$k/$it",
                            BouncyCastleProvider()
                        )
                    }
                }
            }
        }
        val k = GOST3412_2015()

    }

    @Test
    fun testmeta() {
        /*val rnd = Random
        val arr = rnd.nextBytes(20)
        val password = "123456aA"
        val hash_algoritm = "SHA-256"
        val hash_count = 2000
        val cipher_algoritm = "Random"
        val cipher_count = 3
        val cipher_cbc = "GCM"
        val cipher_padding = "NoPadding"
        val en = com.example.releasingwork.Function.Cipher(a)
        en.setPadding(cipher_cbc, cipher_padding)
        val arr_cipher = en.Encrypt()
        //arr_cipher.forEach { print("$it ") }
        val mdin = MetaDataInput(
            arr_cipher,
            password,
            hash_algoritm,
            hash_count,
            cipher_algoritm,
            cipher_cbc,
            cipher_padding,
            cipher_count,
            en.getIV(),
            false
        )
        mdin.setRandomCipher(en.getRandom())

        val mas = mdin.metaData()

        println()
        val mdout = MetaDataOutput(mas, password)
        val arr1 = mdout.metaData()
        //arr1.forEach { print("$it ") }
        println(mdout.getProvader())
        println(mdout.getAlgHash())
        println(mdout.getHashCount())
        println(mdout.getFlagSalt())
        println(mdout.getAlgCipher())
        println(mdout.getCBC())
        println(mdout.getPadding())
        println(mdout.getCipherCount())
        val hash1 =
            Hash(password, mdout.getAlgHash(), mdout.getHashCount(), mdout.getFlagSalt(), false)
        hash1.setProvaderBC(false)
        val hs1 = hash1.Hash()
        val dec = com.example.releasingwork.Function.Cipher(
            mdout.getAlgCipher(),
            arr1,
            hs1,
            mdout.getCipherCount(),
            false
        )
        dec.setPadding(mdout.getCBC(), mdout.getPadding())
        dec.setIV(mdout.getIV())
        val arr_clear = dec.Decrypt()
        //arr_clear.forEach { print("$it ") }
        mdout.getRandomCipher()!!.forEach { print("$it") }
        arr.forEachIndexed { index, byte -> assertEquals(byte, arr_clear[index]) }
        //mdout.getIV().forEach { print("$it ") }*/

    }

    private fun random(flag: Boolean): Byte {
        val rnd = Random
        var fl = rnd.nextInt()
        while ((fl % 2 == 0) == flag) {
            //print("${fl.toByte()} ")
            fl = rnd.nextInt()
        }
        return fl.toByte()
    }

    @Test
    fun RND() {
        //val setalg = listOf("AES", "Blowfish", "DES", "DESede", "RC4")
        val cipher_alg_bc = listOf(/*"AES",
            "Blowfish",
            "DES",
            "DESede",
            "RC4",*/
            "Camellia",
            "CAST5",
            "CAST6",
            "GOST28147",
            "IDEA",
            "Grain128",
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
            "RC5",
            "RC6",
            "HC128",
            "HC256",
            "ChaCha",
            "Salsa20",
            "XSalsa20",
            "VMPC",
            "Grainv1",
            "ARIA",
            "DSTU7624",
            "GCM",
            "Threefish-256",
            "Threefish-512",
            "Threefish-1024"
        )
        cipher_alg_bc.forEach {
            val k = '"'
            var str = "Pair($k$it$k, listOf("
            var t = 0
            var t1 = 0
            var t2 = 0
            var t3 = 1
            var flag = true
            for (i in 1..128) {
                try {
                    val password = "12345"
                    val rnd = Random
                    val hash = MessageDigest.getInstance("SHA-512")
                    val k = rnd.nextBytes(128)
                    val key = SecretKeySpec(k, 0, i, it)
                    val cipher = Cipher.getInstance(it, BouncyCastleProvider())
                    cipher.init(Cipher.ENCRYPT_MODE, key)
                    if (flag) {
                        t = i
                        flag = false
                    }
                    t1 = i
                    t2 = i - t3
                    t3 = i
                } catch (e: InvalidKeyException) {
                } catch (e: IllegalArgumentException) {

                }
            }
            str = if (t == t1) str.plus("$t, 0, $t1)),")
            else str.plus("$t, $t2, $t1)),")
            println(str)
        }
    }

    @Test
    fun RC5() {
        val rnd = Random
        val k = rnd.nextBytes(1000)
        val key = SecretKeySpec(k, 1, 32, "RC5-64")
        val cipher = Cipher.getInstance("RC5-64", BouncyCastleProvider())
        //cipher.init(Cipher.ENCRYPT_MODE, key, RC5ParameterSpec)
    }
}