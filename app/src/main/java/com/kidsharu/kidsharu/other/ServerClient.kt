package com.kidsharu.kidsharu.other

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import com.kidsharu.kidsharu.model.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import kotlin.concurrent.thread


object ServerClient {
    private const val HOST = "https://fc3i3hiwel.execute-api.ap-northeast-2.amazonaws.com/develop"
    private val JSON = MediaType.parse("application/json; charset=utf-8")
    private val JPEG = MediaType.parse("image/jpeg")
    private val client = OkHttpClient()
    private val handler = Handler()

    private var accessToken = ""
    private var teacherId = 0
    private var parentId = 0
    var isTeacher: Boolean = false; private set

    private fun request(parameter: String, path: String, method: Method, callback: (Int, JSONObject, JSONArray) -> Unit) {
        thread {
            val url = when (method) {
                Method.GET -> "$HOST$path?$parameter"
                else -> "$HOST$path"
            }

            val body = RequestBody.create(JSON, parameter)

            val request = Request.Builder().url(url).apply {
                when (method) {
                    Method.POST -> post(body)
                    Method.GET -> get()
                    Method.PUT -> put(body)
                    Method.DELETE -> delete(body)
                }
            }.build()

            val response = client.newCall(request).execute()
            val string = response.body()?.string()
            println("<HTTP> $parameter $path $method $string")

            val jsonObject = try {
                JSONObject(string)
            } catch (_: JSONException) {
                JSONObject()
            }

            val jsonArray = try {
                JSONArray(string)
            } catch (_: JSONException) {
                JSONArray()
            }

            handler.post {
                callback(response.code(), jsonObject, jsonArray)
            }
        }
    }

    // auth
    fun teacherRegister(id: String,
                        password: String,
                        name: String,
                        callback: (String?) -> Unit) {
        val path = "/teachers"
        val parameter = JSONObject().apply {
            put("id", id)
            put("password", CryptoUtil.sha256(password))
            put("name", name)
        }.toString()

        request(parameter, path, Method.POST) { code, json, _ ->
            when (code) {
                200 -> {
                    accessToken = json.getString("access_token")
                    teacherId = json.getInt("teacher_id")
                    callback(null)
                }
                else -> callback(json.getString("msg"))
            }
        }
    }

    fun parentRegister(id: String,
                       password: String,
                       inviteCode: String,
                       callback: (String?) -> Unit) {
        val path = "/parents"
        val parameter = JSONObject().apply {
            put("id", id)
            put("password", CryptoUtil.sha256(password))
            put("invite_code", inviteCode)
        }.toString()

        request(parameter, path, Method.POST) { code, json, _ ->
            when (code) {
                200 -> {
                    accessToken = json.getString("access_token")
                    parentId = json.getInt("parent_id")
                    callback(null)
                }
                else -> callback(json.getString("msg"))
            }
        }
    }

    fun login(id: String,
              password: String,
              callback: (String?) -> Unit) {
        val path = "/auth"
        val parameter = JSONObject().apply {
            put("id", id)
            put("password", CryptoUtil.sha256(password))
        }.toString()

        request(parameter, path, Method.POST) { code, json, _ ->
            when (code) {
                200 -> {
                    accessToken = json.getString("access_token")
                    isTeacher = json.getBoolean("is_teacher")
                    if (isTeacher)
                        teacherId = json.getInt("teacher_id")
                    else
                        parentId = json.getInt("parent_id")
                    callback(null)
                }
                else -> callback(json.getString("msg"))
            }
        }
    }

    // child
    fun parentChildList(callback: (Array<Child>, String?) -> Unit) {
        val path = "/parents/$parentId/children"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, array ->
            when (code) {
                200 -> {
                    val childArray = Array(array.length()) { Child(array.getJSONObject(it)) }
                    callback(childArray, null)
                }
                else -> callback(emptyArray(), json.getString("msg"))
            }
        }
    }

    fun teacherChildList(callback: (Array<Child>, String?) -> Unit) {
        val path = "/teachers/$teacherId/children"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, array ->
            when (code) {
                200 -> {
                    val childArray = Array(array.length()) { Child(array.getJSONObject(it)) }
                    callback(childArray, null)
                }
                else -> callback(emptyArray(), json.getString("msg"))
            }
        }
    }

    // album
    fun teacherAlbumList(callback: (Array<Album>, String?) -> Unit) {
        val path = "/teachers/$teacherId/albums"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, array ->
            when (code) {
                200 -> {
                    val albumArray = Array(array.length()) { Album(array.getJSONObject(it)) }
                    callback(albumArray, null)
                }
                else -> callback(emptyArray(), json.getString("msg"))
            }
        }
    }

    fun teacherAlbumAdd(title: String,
                        content: String,
                        callback: (Album?, String?) -> Unit) {
        val path = "/teachers/$teacherId/albums"
        val parameter = JSONObject().apply {
            put("title", title)
            put("content", content)
        }.toString()

        request(parameter, path, Method.POST) { code, json, _ ->
            when (code) {
                200 -> callback(Album(json), null)
                else -> callback(null, json.getString("msg"))
            }
        }
    }

    fun parentAlbumList(callback: (Array<Album>, String?) -> Unit) {
        val path = "/parents/$parentId/albums"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, array ->
            when (code) {
                200 -> {
                    val albumArray = Array(array.length()) { Album(array.getJSONObject(it)) }
                    callback(albumArray, null)
                }
                else -> callback(emptyArray(), json.getString("msg"))
            }
        }
    }

    fun albumModify(albumId: Int,
                    title: String? = null,
                    content: String? = null,
                    status: AlbumStatus? = null,
                    callback: (String?) -> Unit) {
        val path = "/albums/$albumId"
        val parameter = JSONObject().apply {
            if (title != null) put("title", title)
            if (content != null) put("content", content)
            if (status != null) put("status", status.toString())
        }.toString()

        request(parameter, path, Method.PUT) { code, json, _ ->
            when (code) {
                204 -> callback(null)
                else -> callback(json.getString("msg"))
            }
        }
    }

    // picture
    fun albumPictureList(albumId: Int,
                         callback: (Array<Picture>, String?) -> Unit) {
        val path = "/albums/$albumId/pictures"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, array ->
            when (code) {
                200 -> {
                    val pictureArray = Array(array.length()) { Picture(array.getJSONObject(it)) }
                    callback(pictureArray, null)
                }
                else -> callback(emptyArray(), json.getString("msg"))
            }
        }
    }

    fun albumPictureAdd(albumId: Int,
                        pictureId: Int,
                        fileName: String,
                        callback: (Picture?, String?) -> Unit) {
        val path = "/albums/$albumId/pictures"
        val parameter = JSONObject().apply {
            put("picture_id", pictureId)
            put("file_name", fileName)
        }.toString()

        request(parameter, path, Method.POST) { code, json, _ ->
            when (code) {
                200 -> callback(Picture(json), null)
                else -> callback(null, json.getString("msg"))
            }
        }
    }

    fun pictureUpload(albumId: Int,
                      path: String,
                      callback: (String?, String?) -> Unit) {
        thread {
            val url = "$HOST/albums/$albumId/image-upload"

            val maxLength = 1200F
            val originBitmap = BitmapFactory.decodeFile(path)
            val resizeRatio = Math.max(maxLength / originBitmap.width, maxLength / originBitmap.height)
            val resizedBitmap = if (resizeRatio < 1) {
                Bitmap.createScaledBitmap(
                        originBitmap,
                        (originBitmap.width * resizeRatio).toInt(),
                        (originBitmap.height * resizeRatio).toInt(),
                        false
                )
            } else {
                originBitmap
            }

            val stream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            val byteArray = stream.toByteArray()

            originBitmap.takeIf { !it.isRecycled }?.recycle()
            resizedBitmap.takeIf { !it.isRecycled }?.recycle()

            val body = RequestBody.create(JPEG, byteArray)
            val request = Request.Builder().url(url).post(body).build()
            val response = client.newCall(request).execute()
            val code = response.code()
            val string = response.body()?.string()
            val json = JSONObject(string)
            println("<HTTP IMAGE> $string")

            handler.post {
                when (code) {
                    200 -> callback(json.getString("url"), null)
                    else -> callback(null, json.getString("msg"))
                }
            }
        }
    }

    // face
    fun faceList(pictureId: Int,
                 callback: (Array<Face>, String?) -> Unit) {
        val path = "/pictures/$pictureId/faces"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, array ->
            when (code) {
                200 -> {
                    val faceArray = Array(array.length()) { Face(array.getJSONObject(it)) }
                    callback(faceArray, null)
                }
                else -> callback(emptyArray(), json.getString("msg"))
            }
        }
    }

    fun faceDetail(faceId: Int,
                   callback: (Face?, String?) -> Unit) {
        val path = "/faces/$faceId"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, _ ->
            when (code) {
                200 -> {
                    callback(Face(json), null)
                }
                else -> callback(null, json.getString("msg"))
            }
        }

    }

    fun faceModify(faceId: Int,
                   childId: Int? = null,
                   callback: (String?) -> Unit) {
        val path = "/faces/$faceId"
        val parameter = JSONObject().apply {
            if (childId != null) put("child_id", childId)
        }.toString()

        request(parameter, path, Method.PUT) { code, json, _ ->
            when (code) {
                204 -> {
                    callback(null)
                }
                else -> callback(json.getString("msg"))
            }
        }
    }

    private enum class Method {
        POST,
        GET,
        PUT,
        DELETE
    }
}