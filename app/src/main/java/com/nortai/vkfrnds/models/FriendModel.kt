package com.nortai.vkfrnds.models

/**
 * Created by Atom on 17.03.2018.
 */
class FriendModel {
    var name: String

    var surname: String

    var city: String?

    var avatar: String?

    var isOnline: Boolean

    constructor(name: String, surname: String, city: String?, avatar: String?, isOnline: Boolean) {
        this.name = name
        this.surname = surname
        this.city = city
        this.avatar = avatar
        this.isOnline = isOnline
    }
}