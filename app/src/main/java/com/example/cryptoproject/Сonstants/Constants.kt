@file:Suppress("PackageName", "DEPRECATION")

package com.example.cryptoproject.Сonstants

import android.annotation.SuppressLint
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory

const val NotUse = "Не использовать"
@SuppressLint("SdCardPath")
const val PATH_KEY_STORE = "/data/data/com.example.cryptoproject/my_keystore.ks"
const val KEY_STORE_ALGORITHM = "PKCS12"
const val ALGORITHM = "SHA256withRSA"
const val RSA = "RSA"
const val BlockSize = 128
const val X509 = "X.509"
val CertificatesPath = getExternalStorageDirectory().path + "/RWork/Certificates/"
const val SHA512 = "SHA-512"
const val ECB = "ECB"
const val CBC = "CBC"
const val CCM = "CCM"
const val OCB = "OCB"
const val CipherAlgorithm = "cipherAlgorithm"
const val AES = "AES"
const val SHA256 = "SHA-256"
const val CipherCount = "cipherCount"
const val COUNT = 1
const val KEYSIZE = 32
const val NOT = false
const val HashAlgorithm = "hashAlgorithm"
const val HashCount = "hashCount"
const val Salt = "salt"
const val BCM = "bcm"
const val Padding = "padding"
const val NoPadding = "NoPadding"
const val KeySize = "keySize"
const val Signature = "signature"
const val CipherPassword = "cipherPassword"
const val EnterPassword = "Введите пароль от хранилища ключей!"

