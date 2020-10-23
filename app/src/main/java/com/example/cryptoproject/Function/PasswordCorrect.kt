package com.example.cryptoproject.Function

class PasswordCorrect(password : String) {
    private val password : String
    init {
        this.password = password
    }
    fun PassCorrekt(): Boolean {
        val Reg1 = Regex("[a-z]+")
        val Reg2 = Regex("[A-Z]+")
        return password.length > 7 && Reg1.containsMatchIn(password) && Reg2.containsMatchIn(password)
    }
}