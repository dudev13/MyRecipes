package com.br.domain.model

import com.br.domain.entity.User

class UserFactory {
    fun create(user: UserFake) = when (user) {
        UserFake.Edu -> {
            User(
                name = "Eduardo",
                email = "eduardo@gmail.com",
                password = "123456@",
                phone = "19 9 9678-5151"
            )
        }
        UserFake.Duda ->{
            User(
                name = "Maria Eduarda",
                email = "duda@gmail.com",
                password = "ED123456",
                phone = "19 9 9658-5050"
            )
        }

    }

    sealed class UserFake {
        data object Edu : UserFake()
        data object Duda : UserFake()
    }

}