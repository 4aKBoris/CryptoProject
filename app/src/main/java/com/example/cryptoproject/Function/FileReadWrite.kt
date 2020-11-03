@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import java.io.*

class FileReadWrite {
    fun writeFile(FileName: String, arr: ByteArray) {
        val bw = BufferedOutputStream(FileOutputStream(File(FileName)))
        bw.write(arr)
        bw.close()
    }

    fun readFile(FileName: String): ByteArray {
        val br = BufferedInputStream(FileInputStream(File(FileName)))
        return br.readBytes()
    }
}