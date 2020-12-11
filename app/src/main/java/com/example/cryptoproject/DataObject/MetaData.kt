@file:Suppress("PackageName")

package com.example.cryptoproject.DataObject

import com.example.cryptoproject.Сonstants.BlockSize

object MetaData {
    lateinit var hash_alg: String
    var hash_count = 0
    var salt: ByteArray? = null
    lateinit var cipher_alg: String
    var cipher_count = 0
    var iv = ByteArray(BlockSize)
    var flag_salt = false
    var bcm = ""
    var padding = ""
    var keysize = 32
    var zeroByte = 0
    var cipher_password = false
    var password = ""
    lateinit var array: ByteArray
    lateinit var signature: String
}