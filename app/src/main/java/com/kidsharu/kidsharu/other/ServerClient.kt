package com.kidsharu.kidsharu.other

import android.os.Handler
import co.metalab.asyncawait.async
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.nio.file.Files.delete
import kotlin.concurrent.thread


object ServerClient {
    private val client = OkHttpClient()
    private val handler = Handler()
    private val JSON = MediaType.parse("application/json; charset=utf-8")
    private val HOST = "https://fc3i3hiwel.execute-api.ap-northeast-2.amazonaws.com/develop"

    private var accessToken: String = ""

    private fun request(parameter: String, path: String, method: Method, callback: (Int, JSONObject) -> Unit) {
        val url = when (method) {
            Method.GET -> "$HOST/$path/$parameter"
            else -> "$HOST/$path"
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

        val responseJson = try {
            JSONObject(response.body()!!.string())
        } catch (_: JSONException) {
            JSONObject()
        }

        handler.post {
            callback(response.code(), responseJson)
        }
    }

    fun teacherRegister(id: String,
                        password: String,
                        name: String,
                        callback: (String?) -> Unit) {
        thread {
            val path = "/teachers"
            val parameter = JSONObject().apply {
                put("id", id)
                put("password", CryptoUtil.sha256(password))
                put("name", name)
            }.toString()

            request(parameter, path, Method.POST) { code, json ->
                when (code) {
                    200 -> {
                        accessToken = json.getString("access_token")
                        callback(null)
                    }
                    else -> callback(json.getString("msg"))
                }
            }
        }
    }

    fun parentRegister(id: String,
                       password: String,
                       inviteCode: String,
                       callback: (String?) -> Unit) {
        thread {
            val path = "/parents"
            val parameter = JSONObject().apply {
                put("id", id)
                put("password", CryptoUtil.sha256(password))
                put("invite_code", inviteCode)
            }.toString()

            request(parameter, path, Method.POST) { code, json ->
                when (code) {
                    200 -> {
                        accessToken = json.getString("access_token")
                        callback(null)
                    }
                    else -> callback(json.getString("msg"))
                }
            }
        }
    }

    fun teacherLogin(id: String,
                     password: String,
                     callback: (String?) -> Unit) {
        thread {
            val path = "/teachers/auth"
            val parameter = JSONObject().apply {
                put("id", id)
                put("password", CryptoUtil.sha256(password))
            }.toString()

            request(parameter, path, Method.POST) { code, json ->
                when (code) {
                    200 -> {
                        accessToken = json.getString("access_token")
                        callback(null)
                    }
                    else -> callback(json.getString("msg"))
                }
            }
        }
    }

    fun parentLogin(id: String,
                    password: String,
                    callback: (String?) -> Unit) {
        thread {
            val path = "/parents/auth"
            val parameter = JSONObject().apply {
                put("id", id)
                put("password", CryptoUtil.sha256(password))
            }.toString()

            request(parameter, path, Method.POST) { code, json ->
                when (code) {
                    200 -> {
                        accessToken = json.getString("access_token")
                        callback(null)
                    }
                    else -> callback(json.getString("msg"))
                }
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