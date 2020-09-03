package com.dnd.kindit.retrofit.domain.response

import com.google.gson.annotations.SerializedName

class SearchItemsResponse : CommonResponse() {

    lateinit var data : List<Data>

    inner class Data {
        var id: Long = 0L
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