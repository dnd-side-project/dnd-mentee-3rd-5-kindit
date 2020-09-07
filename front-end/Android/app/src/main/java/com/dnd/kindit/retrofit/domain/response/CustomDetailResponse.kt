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
        @SerializedName("base_menu")
        var baseMenu : List<BaseMenu> = ArrayList()
        var ingredient : List<Ingredient> = ArrayList()
        var brand : String = ""
        var title : String = ""
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
        @SerializedName("rating_user")
        var ratingUser : List<Int> = ArrayList()
        var likes : List<String> = ArrayList()



        inner class BaseMenu{
            var id : Int = 0
            var brand : String = ""
            var name : String = ""
            @SerializedName("menu_type")
            var menuType : Int = 0
            var price : Int = 0
            @SerializedName("menu_image")
            var menuImage : String = ""

            override fun toString(): String {
                return "BaseMenu(id=$id, brand='$brand', name='$name', menuType=$menuType, price=$price, menuImage='$menuImage')"
            }
        }

        inner class Ingredient{
            var id : Int = 0
            var brand : String = ""
            var name : String = ""
            var ingredientType : Int = 0
            var price : Int = 0
            @SerializedName("ingredient_image")
            var ingredientImage : String = ""

            override fun toString(): String {
                return "Ingredient(id=$id, brand='$brand', name='$name', ingredientType=$ingredientType, price=$price, ingredientImage='$ingredientImage')"
            }
        }

        override fun toString(): String {
            return "Data(id=$id, tags=$tags, writer='$writer', likesCount=$likesCount, baseMenu=$baseMenu, ingredient=$ingredient, brand='$brand', title='$title', price=$price, tip='$tip', rating=$rating, hits=$hits, comments=$comments, uploadImage='$uploadImage', createdData=$createdData, updatedData=$updatedData, deleted=$deleted, ratingUser=$ratingUser, likes=$likes)"
        }
    }

    override fun toString(): String {
        return "CustomDetailResponse(data=$data)"
    }
}