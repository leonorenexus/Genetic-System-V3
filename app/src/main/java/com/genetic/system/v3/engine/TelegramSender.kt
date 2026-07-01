package com.genetic.system.v3.engine

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class TelegramSender {
    private val client = OkHttpClient()
    private val botToken = BuildConfig.TELEGRAM_BOT
    private val chatId = BuildConfig.TELEGRAM_CHAT
    
    fun sendMessage(message: String): Boolean {
        if (botToken.isEmpty() || chatId.isEmpty()) return false
        try {
            val url = "https://api.telegram.org/bot$botToken/sendMessage"
            val json = JSONObject().apply { put("chat_id", chatId); put("text", message) }
            val request = Request.Builder().url(url)
                .post(okhttp3.RequestBody.create("application/json".toMediaType(), json.toString())).build()
            client.newCall(request).execute().use { return it.isSuccessful }
        } catch (e: Exception) { return false }
    }
    
    fun sendFile(fileName: String): Boolean {
        if (botToken.isEmpty() || chatId.isEmpty()) return false
        try {
            val file = File(fileName)
            if (!file.exists()) return false
            val url = "https://api.telegram.org/bot$botToken/sendDocument"
            val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("document", file.name, file.asRequestBody("application/json".toMediaType()))
                .build()
            val request = Request.Builder().url(url).post(requestBody).build()
            client.newCall(request).execute().use { return it.isSuccessful }
        } catch (e: Exception) { return false }
    }
}
