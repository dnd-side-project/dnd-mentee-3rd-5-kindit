package com.dnd.kindit.retrofit.domain.request

data class UserSignUpRequest(
    var email: String,
    var nickname: String,
    var password1: String,
    var password2: String
) {
    override fun toString(): String {
        return "UserSignUpRequest(email='$email', nickname='$nickname', password1='$password1', password2='$password2')"
    }
}