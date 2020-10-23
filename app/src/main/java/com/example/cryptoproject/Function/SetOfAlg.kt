package com.example.cryptoproject.Function

class SetOfAlg {
    val hash_alg_default = listOf<String>("MD5", "SHA-1", "SHA-256", "SHA-512")
    val hash_alg_bc = listOf<String>("MD2", "MD4", "MD5", "SHA-1", "SHA-256", "SHA-512")

    val keySize = mapOf(
        Pair("AES", listOf(16, 8, 32)),
        Pair("Blowfish", listOf(1, 1, 56)),
        Pair("DES", listOf(8, 1, 8)),
        Pair("DESede", listOf(24, 1, 24)),
        Pair("RC4", listOf(5, 1, 63)),
        Pair("Camellia", listOf(16, 8, 32)),
        Pair("CAST5", listOf(1, 1, 16)),
        Pair("CAST6", listOf(1, 1, 64)),
        Pair("GOST28147", listOf(32, 0, 32)),
        Pair("IDEA", listOf(1, 1, 128)),
        Pair("Grain128", listOf(16, 1, 128)),
        Pair("Noekeon", listOf(16, 1, 128)),
        Pair("Rijndael", listOf(16, 4, 32)),
        Pair("SEED", listOf(16, 1, 128)),
        Pair("Shacal2", listOf(16, 8, 64)),
        Pair("Serpent", listOf(4, 4, 64)),
        Pair("Skipjack", listOf(10, 1, 128)),
        Pair("SM4", listOf(16, 0, 16)),
        Pair("TEA", listOf(16, 0, 16)),
        Pair("XTEA", listOf(16, 0, 16)),
        Pair("Twofish", listOf(8, 1, 39)),
        Pair("RC2", listOf(1, 1, 128)),
        Pair("RC5", listOf(1, 1, 128)),
        Pair("RC6", listOf(1, 1, 128)),
        Pair("HC128", listOf(16, 0, 16)),
        Pair("HC256", listOf(16, 16, 32)),
        Pair("ChaCha", listOf(16, 16, 32)),
        Pair("Salsa20", listOf(16, 16, 32)),
        Pair("XSalsa20", listOf(32, 0, 32)),
        Pair("VMPC", listOf(1, 1, 128)),
        Pair("Grainv1", listOf(10, 1, 128)),
        Pair("ARIA", listOf(16, 8, 32)),
        Pair("DSTU7624", listOf(16, 16, 32)),
        Pair("GCM", listOf(16, 8, 32)),
        Pair("Threefish-256", listOf(32, 0, 32)),
        Pair("Threefish-512", listOf(64, 0, 64)),
        Pair("Threefish-1024", listOf(128, 0, 128)),

        )

    val cipher_alg_default = listOf("AES", "Blowfish", "DES", "DESede", "RC4")
    val cipher_alg_bc = listOf("AES",
    "Blowfish",
    "DES",
    "DESede",
    "RC4",
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
    "Threefish-1024")

    val cipher_bcm_default = listOf(
        "ECB", "CBC", "OFB", "CFB", "CTR",
    )
    val cipher_bcm_bc = listOf(
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

    val cipher_padding_default = listOf("NoPadding", "PKCS5Padding", "ISO10126Padding")

    val cipher_padding_bc = listOf(
        "NoPadding",
        "PKCS5Padding",
        "ISO10126Padding",
        "PKCS7Padding",
        "ISO10126-2Padding",
        "ISO7816-4Padding",
        "ISO9797-1Padding",
        "X923Padding",
        "X9.23Padding",
        "TBCPadding",
        "ZeroBytePadding",
        "withCTS"
    )

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

     val cbc128 = setOf("CCM", "GCM", "OCB")

     val cipher64 = setOf(
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

     val cipher128 = setOf(
        "AES",
        "ARIA",
        "Camellia",
        "CAST6",
        "DSTU7624",
        "Noekeon",
        "RC5-64",
        "RC-6",
        "Rijndael",
        "SEED",
        "Serpent",
        "SM4",
        "Twofish"
    )
}