package com.dnd.kindit.retrofit.domain.response

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class CustomDetailResponse : CommonResponse(){

    lateinit var data : Data

    inner class Data{
        var id : Int = 0
        var tags : List<String> = ArrayList()
        var writer : String = ""
        @SerializedName("likes_count")
        var likesCount : Int = 0
        var brand : String = ""
        var title : String = ""
        @SerializedName("base_menu")
        var baseMenu : String = ""
        var price : Int = 0
        var tip : String = ""
        var rating : Float = 0.0f
        var hits : Int = 0
        var comments : Int = 0
        @SerializedName("upload_image")
        var uploadImage : String = ""
        @SerializedName("created_data")
        var createdData : Date = Date()
        @SerializedName("updated_data")
        var updatedData : Date = Date()
        var deleted : Boolean = false
        var ingredient : List<String> = ArrayList()
        @SerializedName("rating_user")
        var ratingUser : List<String> = ArrayList()
        var likes : List<String> = ArrayList()

        override fun toString(): String {
            return "Data(id=$id, tags=$tags, writer='$writer', likesCount=$likesCount, brand='$brand', title='$title', baseMenu='$baseMenu', price=$price, tip='$tip', rating=$rating, hits=$hits, comments=$comments, uploadImage='$uploadImage', createdData=$createdData, updatedData=$updatedData, deleted=$deleted, ingredient=$ingredient, ratingUser=$ratingUser, likes=$likes)"
        }
    }

    override fun toString(): String {
        return "CustomDetailResponse(data=$data)"
    }
}