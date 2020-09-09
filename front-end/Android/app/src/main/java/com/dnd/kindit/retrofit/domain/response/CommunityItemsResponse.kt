package com.dnd.kindit.retrofit.domain.response

import com.google.gson.annotations.SerializedName

class CommunityItemsResponse : CommonResponse(){
    lateinit var data : List<Data>

    inner class Data{
        var id : Int = 0
        var writer : String = ""
        var content : String = ""
        @SerializedName("likes_count")
        var likesCount : Int = 0
        var comments : Int = 0
        var hits : Int = 0
        @SerializedName("upload_image")
        var uploadImage : String  = ""
        @SerializedName("created_date")
        var createdDate : String = ""
        override fun toString(): String {
            return "Data(id=$id, writer='$writer', content='$content', likesCount=$likesCount, comments=$comments, hits=$hits, uploadImage='$uploadImage', createdDate='$createdDate')"
        }
    }

    override fun toString(): String {
        return "CommunityItemsResponse(data=$data)"
    }

}