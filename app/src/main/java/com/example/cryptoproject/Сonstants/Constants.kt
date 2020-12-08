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
