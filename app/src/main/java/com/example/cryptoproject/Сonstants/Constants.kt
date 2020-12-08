@file:Suppress("PackageName", "DEPRECATION")

package com.example.cryptoproject.Сonstants

import android.annotation.SuppressLint
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import javax.crypto.Cipher

const val NotUse = "Не использовать"
@SuppressLint("SdCardPath")
const val PATH_KEY_STORE = "/data/data/com.example.cryptoproject/my_keystore.ks"
const val KEY_STORE_ALGORITHM = "PKCS12"
const val ALGORITHM = "SHA256withRSA"
const val RSA = "RSA"
const val PERMISSION_REQUEST_CODE = 0
const val BlockSize = 128
const val X509 = "X.509"
val CertificatesPath = getExternalStorageDirectory().path + "/RWork/Certificates/"
const val SHA512 = "SHA-512"
const val ECB = "ECB"
const val CBC = "CBC"
const val CCM = "CCM"
const val OCB = "OCB"
const val GOFB = "GOFB"
const val CTR = "CTR"
const val GOST34122015 = "GOST3412-2015"
const val CipherAlgorithm = "cipherAlgorithm"
const val AES = "AES"
const val SHA256 = "SHA-256"
const val CipherCount = "cipherCount"
const val ONE = 1
const val KEYSIZE = 32
const val NOT = false
const val HashAlgorithm = "hashAlgorithm"
const val HashCount = "hashCount"
const val Salt = "salt"
const val BCM = "bcm"
const val Padding = "padding"
const val PKCS5Padding = "PKCS5Padding"
const val NoPadding = "NoPadding"
const val KeySize = "keySize"
const val Signature = "signature"
const val CipherPassword = "cipherPassword"
const val EnterPasswordKeyStore = "Введите пароль от хранилища ключей!"
const val TEN = 1000000000
const val RWork = "RWork"
const val RCipher = "RWork/Cipher"
const val RClear = "RWork/Clear_files"
const val RCertificates = "RWork/Certificates"
const val SecordPassword = "secordPassword"
const val DeleteFile = "deleteFile"
const val PasswordFlag = "passwordFlag"
const val Cansel = "Отмена"
const val AlgHash = "Алгоритм хэширования"
const val NotRequirements = "Введённое значение не соответствует требованиям!"
const val AlgCipher = "Алгоритм шифрования"
const val WithCTS = "withCTS"
const val PaddingMode = "Режим сцепления блоков"
const val FillingMode = "Режим наполнения"
const val Sign = "Цифровая подпись"
const val Mistake = "Ошибка!"
const val Exc = "Exception"
const val CertSelect = "Выбор сертификата"
const val OpenFile = "Откройте файл!"
const val EnterPassword = "Введите пароль!"
const val RequirementsPassword = "Пароль не соответствует требованиям!"
const val CoincidePassword = "Введённые пароли не совпадают!"
const val CertificSelect = "Выберите сертификат для шифрования пароля!"
const val NotExist = "Файла не существует!"
const val SelectNotFile = "Вы выбрали не файл!"
const val LowMemory = "Слишком мало памяти!"
val CipherPath = getExternalStorageDirectory().path + "/RWork/Cipher/"
const val FileEncrypted = "Файл зашифрован!"
