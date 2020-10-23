package com.example.cryptoproject.Function

class SetOfAlg {
    val hash_alg_default = listOf<String>("MD5", "SHA-1", "SHA-256", "SHA-512")
    val hash_alg_bc = listOf<String>("MD2", "MD4", "MD5", "SHA-1", "SHA-256", "SHA-512")

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
    "AESWrap",
    "ARIA",
    "ARIAWrap",
    "CamelliaWrap",
    "DESedeWrap",
    "DSTU7624",
    "GCM",
    "SEEDWrap",
    "Threefish-256",
    "Threefish-512",
    "Threefish-1024",
    "RC5-64")

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
        "AESWrap",
        "ARIAWrap",
        "CamelliaWrap",
        "DESedeWrap",
        "DSTU7624Wrap",
        "SEEDWrap",
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