package com.dnd.kindit.retrofit.domain.response

import com.google.gson.annotations.SerializedName
import java.util.*

class UserResponse : CommonResponse() {
    lateinit var user: User

    inner class User {
        var email: String = ""
        var nickname: String = ""
        @SerializedName("date_joined")
        var dateJoined: Date? = null

        override fun toString(): String {
            return "User(email='$email', nickname='$nickname', dateJoined=$dateJoined)"
        }
    }

    override fun toString(): String {
        return "UserResponse(user=$user)"
    }
}