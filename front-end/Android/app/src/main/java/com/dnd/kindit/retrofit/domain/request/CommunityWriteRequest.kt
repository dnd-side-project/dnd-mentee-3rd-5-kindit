package com.dnd.kindit.retrofit.domain.request

class CommunityWriteRequest(var content : String) {
    override fun toString(): String {
        return "CommunityWriteRequest(content='$content')"
    }
}