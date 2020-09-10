package com.dnd.kindit.arch.model

class CommunityItem {
    var id: Int = 0
    var nickname: String? = ""
    var content: String = ""
    var menuPic: String? = ""
    var date: String = ""
    var lickCount: Int = 0
    var replyCount: Int = 0
    var viewCount: Int = 0

    constructor(id: Int, nickname: String?, content: String, menuPic: String?, date: String, lickCount: Int, replyCount: Int, viewCount: Int) {
        this.id = id
        this.nickname = nickname
        this.content = content
        this.menuPic = menuPic
        this.date = date
        this.lickCount = lickCount
        this.replyCount = replyCount
        this.viewCount = viewCount
    }

    override fun toString(): String {
        return "CommunityItem(id=$id, nickname='$nickname', content='$content', menuPic='$menuPic', date='$date', lickCount=$lickCount, replyCount=$replyCount, viewCount=$viewCount)"
    }
}