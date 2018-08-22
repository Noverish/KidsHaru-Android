package com.kidsharu.kidsharu.other

import android.os.Handler
import com.kidsharu.kidsharu.model.Album
import com.kidsharu.kidsharu.model.Picture
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.concurrent.thread


object ServerClient {
    private const val HOST = "https://fc3i3hiwel.execute-api.ap-northeast-2.amazonaws.com/develop"
    private val JSON = MediaType.parse("application/json; charset=utf-8")
    private val client = OkHttpClient()
    private val handler = Handler()

    private var accessToken = ""
    private var teacherId = 1
    private var parentId = 1

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

    fun teacherLogin(id: String,
                     password: String,
                     callback: (String?) -> Unit) {
        val path = "/teachers/auth"
        val parameter = JSONObject().apply {
            put("id", id)
            put("password", CryptoUtil.sha256(password))
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

    fun parentLogin(id: String,
                    password: String,
                    callback: (String?) -> Unit) {
        val path = "/parents/auth"
        val parameter = JSONObject().apply {
            put("id", id)
            put("password", CryptoUtil.sha256(password))
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


    fun teacherAlbumList(callback: (Array<Album>, String?) -> Unit) {
        val path = "/teachers/$teacherId/albums"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, array ->
            when (code) {
                200 -> {
                    val albumArray = Array(array.length()) {
                        Album(array.getJSONObject(it))
                    }
                    callback(albumArray, null)
                }
                else -> callback(emptyArray(), json.getString("msg"))
            }
        }
    }

    fun albumPictureList(albumId: Int,
                         callback: (Array<Picture>, String?) -> Unit) {
        val path = "/albums/$albumId/pictures"
        val parameter = ""

        request(parameter, path, Method.GET) { code, json, array ->
            when (code) {
                200 -> {
                    val pictureArray = Array(array.length()) {
                        Picture(array.getJSONObject(it))
                    }
                    callback(pictureArray, null)
                }
                else -> callback(emptyArray(), json.getString("msg"))
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