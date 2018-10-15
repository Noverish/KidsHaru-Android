package com.kidsharu.kidsharu.model

import org.json.JSONObject

class Face(
        val faceId: Int,
        val albumId: Int,
        val pictureId: Int,
        val childId: Int?,
        val childName: String?,
        val rectX: Int,
        val rectY: Int,
        val rectWidth: Int,
        val rectHeight: Int
) {
    constructor(json: JSONObject) : this(
            json.getInt("face_id"),
            json.getInt("album_id"),
            json.getInt("picture_id"),
            if (json.isNull("child_id")) null else json.getInt("child_id"),
            if (json.isNull("child_name")) null else json.getString("child_name"),
            json.getInt("rect_x"),
            json.getInt("rect_y"),
            json.getInt("rect_width"),
            json.getInt("rect_height")
    )

    override fun toString(): String {
        return "Face(faceId=$faceId, albumId=$albumId, pictureId=$pictureId, childId=$childId, childName='$childName', rectX=$rectX, rectY=$rectY, rectWidth=$rectWidth, rectHeight=$rectHeight)"
    }
}