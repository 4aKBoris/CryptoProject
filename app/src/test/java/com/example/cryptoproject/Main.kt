@file:Suppress("NAME_SHADOWING")

package com.example.cryptoproject

import com.example.cryptoproject.Function.SetOfAlg

fun main() {
    val s = SetOfAlg()
    s.sign.forEachIndexed { index, s ->  println("Pair(\"$s\", $index),")}
    println()
}