package com.dnd.kindit.retrofit.domain.request

data class UserLoginRequest(
    var email: String,
    var password: String
) {
    override fun toString(): String {
        return "UserLoginRequest(email='$email', password='$password')"
    }
}