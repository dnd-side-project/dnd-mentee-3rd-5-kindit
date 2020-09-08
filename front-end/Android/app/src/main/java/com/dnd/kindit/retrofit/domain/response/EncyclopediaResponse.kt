package com.dnd.kindit.retrofit.domain.response

import com.google.gson.annotations.SerializedName

class EncyclopediaResponse : CommonResponse() {

    lateinit var data : List<SearchItemsResponse.Data>

    inner class Data {
        var id: Int = 0
        var title: String = ""
        var writer: String = ""
        var hits: Long = 0L
        var rating: Float = 0.0f
        @SerializedName("upload_image")
        var uploadImage: String = ""

        override fun toString(): String {
            return "Data(id=$id, title='$title', writer='$writer', hits=$hits, rating=$rating, uploadImage='$uploadImage')"
        }
    }

    override fun toString(): String {
        return "SearchItemsResponse(data=$data)"
    }
}