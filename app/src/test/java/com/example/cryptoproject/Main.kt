@file:Suppress("NAME_SHADOWING")

package com.example.cryptoproject

import com.example.cryptoproject.Сonstants.sign

fun main() {
    sign.forEachIndexed { index, s ->  println("Pair(\"$s\", $index),")}
    println()
}

class MyClass {
    val k = 1
    companion object {
    }
}
