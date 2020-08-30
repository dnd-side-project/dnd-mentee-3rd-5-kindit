package com.dnd.kindit.arch.model

class SearchItem {
    var name: String = ""
    var viewCount: Long = 0L
    var userName: String = ""
    var starCount: Long = 0L
    var imgPic: String = ""

    constructor(name: String, viewCount: Long, userName: String, starCount: Long, imgPic: String) {
        this.name = name
        this.viewCount = viewCount
        this.userName = userName
        this.starCount = starCount
        this.imgPic = imgPic
    }

    override fun toString(): String {
        return "SearchItem(name='$name', viewCount=$viewCount, userName='$userName', starCount=$starCount, imgPic='$imgPic')"
    }
}