package com.br.domain.services.password

import at.favre.lib.crypto.bcrypt.BCrypt

class BCryptPasswordService {

    fun verifyPassword(password: CharArray, hashPassword: String):Boolean {
        return BCrypt.verifyer().verify(password, hashPassword).verified
    }

    fun hashedPassword(const: Int, password: String):String {
        return BCrypt.withDefaults().hashToString(const, password.toCharArray()).toString()
    }
}