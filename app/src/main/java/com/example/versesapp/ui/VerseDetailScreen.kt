// C:\app\src\main\java\com\example\versesapp\ui\VerseDetailScreen.kt
package com.example.versesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.versesapp.model.Verse

/**
 * Displays all language translations for a single verse.
 * Requirement: 4 lines of Kannada, separator, 4 lines of Transliteration, separator, 4 lines of Translation.
 */
@Composable
fun VerseDetailScreen(verse: Verse) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${verse.chapter} - Verse ${verse.verseNumber.toInt()}") }
                // Add navigation icon/back button here in a real app
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Enable scrolling for long verses/translations
                .padding(16.dp)
        ) {
            // Helper function to display a section (Kannada, Transliteration, or Translation)
            fun Section(title: String, lines: List<String>) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Divider(modifier = Modifier.padding(vertical = 4.dp))
                lines.forEach { line ->
                    Text(text = line, style = MaterialTheme.typography.bodyLarge)
                }
            }
            
            // 1. KANNADA ORIGINAL
            Section(
                title = "ಕನ್ನಡ ಮೂಲ (Kannada Original)",
                lines = verse.kannadaLines
            )
            
            // SEPARATOR
            Spacer(modifier = Modifier.height(24.dp))
            
            // 2. ENGLISH TRANSLITERATION
            Section(
                title = "English Transliteration",
                lines = verse.transliterationLines
            )
            
            // SEPARATOR
            Spacer(modifier = Modifier.height(24.dp))
            
            // 3. ENGLISH TRANSLATION
            Section(
                title = "English Translation",
                lines = verse.translationLines
            )
            
            // Footer spacer
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}