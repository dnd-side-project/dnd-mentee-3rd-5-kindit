package com.dnd.kindit.retrofit.domain.request

class ProfileUpdateRequest(var nickname: String) {
    override fun toString(): String {
        return "ProfileUpdateRequest(nickname='$nickname')"
    }
}