// C:\data\src\main\kotlin\com\example\versesapp\data\Repository.kt

package com.example.versesapp.data

import android.content.Context
import com.example.versesapp.model.Verse
import com.example.versesapp.model.VerseLine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class VerseRepository(private val context: Context) {

    // List of all expected JSON filenames
    private val jsonFiles = (1..10).map { "verse_${it}.0.json" }
    
    // Lazy initialization ensures data is loaded only when first accessed
    private val allVerses: List<Verse> by lazy {
        loadAndTransformVerses()
    }

    fun getAllVerses(): List<Verse> {
        return allVerses
    }
    
    // Fetches a single verse by its number (e.g., for detail screen)
    fun getVerseByNumber(number: Double): Verse? {
        return allVerses.find { it.verseNumber == number }
    }

    private fun loadAndTransformVerses(): List<Verse> {
        val allLines = mutableListOf<VerseLine>()
        val gson = Gson()
        val listVerseLineType = object : TypeToken<List<VerseLine>>() {}.type

        // 1. Load and Parse All JSON Files
        for (fileName in jsonFiles) {
            try {
                // Read file from the 'assets/json' directory
                val inputStream = context.assets.open("json/$fileName")
                val reader = InputStreamReader(inputStream)
                
                // Parse the JSON array into a List<VerseLine>
                val lines: List<VerseLine> = gson.fromJson(reader, listVerseLineType)
                allLines.addAll(lines)
                
                reader.close()
            } catch (e: Exception) {
                // Log or handle the case where a file is missing or corrupted
                println("Error reading or parsing $fileName: ${e.message}")
            }
        }

        // 2. Group and Transform Lines into Verses
        // Group the lines by their verse number
        return allLines
            .groupBy { it.verseNumber }
            .map { (verseNum, lines) ->
                // Sort lines by line_number to ensure correct order
                val sortedLines = lines.sortedBy { it.lineNumber }
                
                Verse(
                    verseNumber = verseNum,
                    // Chapter is the same for all lines in a verse
                    chapter = sortedLines.firstOrNull()?.chapter ?: "Unknown Chapter",
                    // Map the lines to lists of their respective translations
                    kannadaLines = sortedLines.map { it.kannadaOriginal },
                    transliterationLines = sortedLines.map { it.englishTransliteration },
                    translationLines = sortedLines.map { it.englishTranslation }
                )
            }
            // Sort the final list by verse number
            .sortedBy { it.verseNumber }
    }
}