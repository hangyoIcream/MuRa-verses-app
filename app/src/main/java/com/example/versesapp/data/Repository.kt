package com.example.versesapp.data

import android.content.Context
import com.example.versesapp.model.VerseLine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class Repository(private val context: Context, private val baseUrl: String) {
    private val gson = Gson()
    private val cache = mutableMapOf<Int, List<VerseLine>>()

    suspend fun fetchVerse(verseNum: Int): List<VerseLine>? {
        cache[verseNum]?.let { return it }
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("${baseUrl}verse_${verseNum}.0.json")
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 15000
                conn.readTimeout = 15000
                conn.requestMethod = "GET"
                conn.connect()
                if (conn.responseCode != 200) return@withContext null
                val text = conn.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
                val normalized = text.replace("NaN", "null")
                val listType = object : TypeToken<List<VerseLine>>() {}.type
                val lines: List<VerseLine> = gson.fromJson(normalized, listType)
                cache[verseNum] = lines
                try {
                    File(context.filesDir, "verse_${'$'}{verseNum}.json").writeText(normalized, Charsets.UTF_8)
                } catch (_: Exception) {}
                lines
            } catch (e: Exception) {
                try {
                    val f = File(context.filesDir, "verse_${'$'}{verseNum}.json")
                    if (f.exists()) {
                        val txt = f.readText(Charsets.UTF_8)
                        val normalized = txt.replace("NaN", "null")
                        val listType = object : TypeToken<List<VerseLine>>() {}.type
                        gson.fromJson<List<VerseLine>>(normalized, listType)
                    } else null
                } catch (e2: Exception) { null }
            }
        }
    }
}
