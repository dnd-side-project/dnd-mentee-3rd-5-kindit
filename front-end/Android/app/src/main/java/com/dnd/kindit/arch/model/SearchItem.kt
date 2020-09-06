package com.dnd.kindit.arch.model

class SearchItem {
    var id: Int = 0
    var name: String = ""
    var viewCount: Long = 0L
    var userName: String = ""
    var starCount: Float = 0.0f
    var imgPic: String? = ""

    constructor(id: Int, name: String, viewCount: Long, userName: String, starCount: Float, imgPic: String?) {
        this.id = id
        this.name = name
        this.viewCount = viewCount
        this.userName = userName
        this.starCount = starCount
        this.imgPic = imgPic
    }

    override fun toString(): String {
        return "SearchItem(id=$id, name='$name', viewCount=$viewCount, userName='$userName', starCount=$starCount, imgPic=$imgPic)"
    }


}