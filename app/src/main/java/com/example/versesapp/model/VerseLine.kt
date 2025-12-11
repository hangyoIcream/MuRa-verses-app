// C:\app\src\main\java\com\example\versesapp\model\VerseLine.kt

import com.google.gson.annotations.SerializedName

data class VerseLine(
    // Gson will use these annotations to map JSON keys to Kotlin properties
    @SerializedName("verse_number") val verseNumber: Double,
    @SerializedName("line_number") val lineNumber: Double,
    @SerializedName("chapter") val chapter: String,
    @SerializedName("kannada_original") val kannadaOriginal: String,
    @SerializedName("english_transliteration") val englishTransliteration: String,
    @SerializedName("english_translation") val englishTranslation: String,
    // Note: The `tags` field is nullable as your sample showed 'NaN'
    @SerializedName("tags") val tags: String?
)