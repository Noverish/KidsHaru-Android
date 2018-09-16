package com.kidsharu.kidsharu.model

import org.json.JSONObject

class Face(
        val albumId: Int,
        val pictureId: Int,
        val childId: Int,
        val childName: String,
        val rectX: Int,
        val rectY: Int,
        val rectWidth: Int,
        val rectHeight: Int
) {
    constructor(json: JSONObject) : this(
            json.getInt("album_id"),
            json.getInt("picture_id"),
            json.getInt("child_id"),
            json.getString("child_name"),
            json.getInt("rect_x"),
            json.getInt("rect_y"),
            json.getInt("rect_width"),
            json.getInt("rect_height")
    )

    override fun toString(): String {
        return "Face(albumId=$albumId, pictureId=$pictureId, childId=$childId, childName='$childName', rectX=$rectX, rectY=$rectY, rectWidth=$rectWidth, rectHeight=$rectHeight)"
    }
}