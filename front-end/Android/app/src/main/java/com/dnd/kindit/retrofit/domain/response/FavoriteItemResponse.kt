package com.dnd.kindit.retrofit.domain.response

class FavoriteItemResponse : CommonResponse() {

    lateinit var data: List<Data>

    inner class Data {
        var id: Int = 0
        var name: String = ""

        override fun toString(): String {
            return "Data(id=$id, name='$name')"
        }
    }

    override fun toString(): String {
        return "FavoriteItemResponse(data=$data)"
    }
}