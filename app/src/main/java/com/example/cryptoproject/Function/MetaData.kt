package com.example.cryptoproject.Function

import kotlin.random.Random

open class MetaData(password: String) {

    private val password: String

    init {
        this.password = password
    }

    val rndSeek = Random(IntSeek())

    private fun IntSeek(): Int {
        var i = 0
        password.forEach { i = +it.toInt() }
        return i
    }

    protected val hashfunInput = mapOf<String, Byte>(
        Pair("MD5", 0),
        Pair("SHA-1", 1),
        Pair("SHA-256", 2),
        Pair("SHA-512", 3),
        Pair("MD2", 4),
        Pair("MD4", 5),
    )

    protected val cryptofunInput = mapOf<String, Byte>(
        Pair("AES", 0),
        Pair("Blowfish", 1),
        Pair("DES", 2),
        Pair("DESede", 3),
        Pair("RC4", 4),
        Pair("Camellia", 5),
        Pair("CAST5", 6),
        Pair("CAST6", 7),
        Pair("GOST28147", 8),
        Pair("IDEA", 9),
        Pair("Grain128", 10),
        Pair("Noekeon", 11),
        Pair("Rijndael", 12),
        Pair("SEED", 13),
        Pair("Shacal2", 14),
        Pair("Serpent", 15),
        Pair("Skipjack", 16),
        Pair("SM4", 17),
        Pair("TEA", 18),
        Pair("XTEA", 19),
        Pair("Twofish", 20),
        Pair("RC2", 21),
        Pair("RC5", 22),
        Pair("RC6", 23),
        Pair("HC128", 24),
        Pair("HC256", 25),
        Pair("ChaCha", 26),
        Pair("Salsa20", 27),
        Pair("XSalsa20", 28),
        Pair("VMPC", 29),
        Pair("Grainv1", 30),
        Pair("ARIA", 32),
        Pair("DSTU7624", 36),
        Pair("GCM", 37),
        Pair("Threefish-256", 39),
        Pair("Threefish-512", 40),
        Pair("Threefish-1024", 41)
    )

    protected val cryptoCBCInput = mapOf<String, Byte>(
        Pair("ECB", 0),
        Pair("CBC", 1),
        Pair("OFB", 2),
        Pair("CFB", 3),
        Pair("CTR", 4),
        Pair("OpenPGPCFB", 5),
        Pair("CTS", 6),
        Pair("GOFB", 7),
        Pair("GCFB", 8),
        Pair("CCM", 9),
        Pair("EAX", 10),
        Pair("GCM", 11),
        Pair("OCB", 12)
    )

    protected val cryptoCBCOutput = mapOf<Byte, String>(
        Pair(0, "ECB"),
        Pair(1, "CBC"),
        Pair(2, "OFB"),
        Pair(3, "CFB"),
        Pair(4, "CTR"),
        Pair(5, "OpenPGPCFB"),
        Pair(6, "CTS"),
        Pair(7, "GOFB"),
        Pair(8, "GCFB"),
        Pair(9, "CCM"),
        Pair(10, "EAX"),
        Pair(11, "GCM"),
        Pair(12, "OCB")
    )

    protected val cryptoPaddingInput = mapOf<String, Byte>(
        Pair("NoPadding", 0),
        Pair("PKCS5Padding", 1),
        Pair("ISO10126Padding", 2),
        Pair("PKCS7Padding", 3),
        Pair("ISO10126-2Padding", 4),
        Pair("ISO7816-4Padding", 5),
        Pair("ISO9797-1Padding", 6),
        Pair("X923Padding", 7),
        Pair("X9.23Padding", 8),
        Pair("TBCPadding", 9),
        Pair("ZeroBytePadding", 10),
        Pair("withCTS", 11)
    )

    protected val cryptoPaddingOutput = mapOf<Byte, String>(
        Pair(0, "NoPadding"),
        Pair(1, "PKCS5Padding"),
        Pair(2, "ISO10126Padding"),
        Pair(3, "PKCS7Padding"),
        Pair(4, "ISO10126-2Padding"),
        Pair(5, "ISO7816-4Padding"),
        Pair(6, "ISO9797-1Padding"),
        Pair(7, "X923Padding"),
        Pair(8, "X9.23Padding"),
        Pair(9, "TBCPadding"),
        Pair(10, "ZeroBytePadding"),
        Pair(11, "withCTS")
    )


    protected
    val hashfunOutput = mapOf<Byte, String>(
        Pair(0, "MD5"),
        Pair(1, "SHA-1"),
        Pair(2, "SHA-256"),
        Pair(3, "SHA-512"),
        Pair(4, "MD2"),
        Pair(5, "MD4"),
    )

    protected
    val cryptofunOutput = mapOf<Byte, String>(
        Pair(0, "AES"),
        Pair(1, "Blowfish"),
        Pair(2, "DES"),
        Pair(3, "DESede"),
        Pair(4, "RC4"),
        Pair(5, "Camellia"),
        Pair(6, "CAST5"),
        Pair(7, "CAST6"),
        Pair(8, "GOST28147"),
        Pair(9, "IDEA"),
        Pair(10, "Grain128"),
        Pair(11, "Noekeon"),
        Pair(12, "Rijndael"),
        Pair(13, "SEED"),
        Pair(14, "Shacal2"),
        Pair(15, "Serpent"),
        Pair(16, "Skipjack"),
        Pair(17, "SM4"),
        Pair(18, "TEA"),
        Pair(19, "XTEA"),
        Pair(20, "Twofish"),
        Pair(21, "RC2"),
        Pair(22, "RC5"),
        Pair(23, "RC6"),
        Pair(24, "HC128"),
        Pair(25, "HC256"),
        Pair(26, "ChaCha"),
        Pair(27, "Salsa20"),
        Pair(28, "XSalsa20"),
        Pair(29, "VMPC"),
        Pair(30, "Grainv1"),
        Pair(32, "ARIA"),
        Pair(36, "DSTU7624"),
        Pair(37, "GCM"),
        Pair(39, "Threefish-256"),
        Pair(40, "Threefish-512"),
        Pair(41, "Threefish-1024")
    )

    protected
    val cipherStream = setOf(
        "HC128",
        "RC4",
        "HC256",
        "ChaCha",
        "Salsa20",
        "XSalsa20",
        "VMPC",
        "Grainv1",
        "Grain128",
        "Zuc128",
        "Zuc256"
    )
}