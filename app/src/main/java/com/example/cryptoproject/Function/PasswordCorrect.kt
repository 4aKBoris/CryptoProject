@file:Suppress("PackageName")

package com.example.cryptoproject.Function

class PasswordCorrect(private val password: String) {
    fun PassCorrekt(): Boolean {
        val Reg1 = Regex("[a-z]+")
        val Reg2 = Regex("[A-Z]+")
        return password.length > 7 && Reg1.containsMatchIn(password) && Reg2.containsMatchIn(password)
    }
}