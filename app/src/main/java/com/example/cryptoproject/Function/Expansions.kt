@file:Suppress("PackageName")

package com.example.cryptoproject.Function

import com.example.cryptoproject.Fragments.DecryptFragment
import com.example.cryptoproject.Сonstants.Zero

fun ByteArray.deleteFirst(n : Int) : ByteArray{
    return this.copyOfRange(n, this.size)
}

fun ByteArray.deleteLast(n : Int) : ByteArray{
    return this.copyOfRange(0, n)
}

fun ByteArray.assertIndex() : Boolean {
    var flag = true
    this.forEachIndexed{i , b -> if (i != b.toInt()) flag = false}
    return flag
}

fun ByteArray.createIndex() : ByteArray {
    this.mapIndexed { index, _ -> this[index] = index.toByte() }
    return this
}

fun ByteArray.flag() : Boolean = ((this[0] == Zero && this[1] % 2 != 0) || (this[0] != Zero) && this[this[1].toInt() + 131] % 2 != 0)