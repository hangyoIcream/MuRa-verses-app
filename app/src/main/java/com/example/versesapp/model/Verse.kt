package com.example.versesapp.model

data class VerseLine(
    val verse_number: Double?,
    val line_number: Double?,
    val chapter: String? = null,
    val kannada_original: String? = null,
    val english_transliteration: String? = null,
    val english_translation: String? = null,
    val tags: String? = null
)
