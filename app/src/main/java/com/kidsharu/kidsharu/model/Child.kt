package com.kidsharu.kidsharu.model

import org.json.JSONObject

class Child(
        val childId: Int,
        val profileImgUrl: String,
        val name: String,
        val contact: String
) {
    constructor(json: JSONObject) : this(
            json.getInt("child_id"),
            json.getString("profile_img_url"),
            json.getString("name"),
            json.getString("contact")
    )
}