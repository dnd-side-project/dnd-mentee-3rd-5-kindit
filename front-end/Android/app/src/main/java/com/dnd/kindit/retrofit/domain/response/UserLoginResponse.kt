package com.dnd.kindit.retrofit.domain.response

class UserLoginResponse : CommonResponse() {
    lateinit var user : User

    inner class User{
        var token: String = ""
        var email : String = ""
        var nickname : String = ""

        override fun toString(): String {
            return "User(token='$token', email='$email', nickname='$nickname')"
        }
    }

    override fun toString(): String {
        return "UserLoginResponse(user=$user)"
    }
}