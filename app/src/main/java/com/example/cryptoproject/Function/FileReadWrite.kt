@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import java.io.*

fun writeFile(FileName: String, arr: ByteArray) {
    val bw = BufferedOutputStream(FileOutputStream(File(FileName)))
    bw.write(arr)
    bw.close()
}

fun readFile(FileName: String): ByteArray {
    val br = BufferedInputStream(FileInputStream(File(FileName)))
    return br.readBytes()
}

fun readFileOne(FileName: String): Int {
    val br = BufferedInputStream(FileInputStream(File(FileName)))
    return br.read()
}

fun readFileN(FileName: String, n: Int): ByteArray {
    val br = BufferedInputStream(FileInputStream(File(FileName)))
    val arr = ByteArray(n)
    br.read(arr, 0, n)
    return arr
}