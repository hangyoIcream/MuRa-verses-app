// C:\app\src\main\java\com\example\versesapp\model\Verse.kt
package com.example.versesapp.model

data class Verse(
    val verseNumber: Double,
    val chapter: String,
    val kannadaLines: List<String>,
    val transliterationLines: List<String>,
    val translationLines: List<String>
)