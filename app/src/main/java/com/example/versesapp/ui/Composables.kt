// C:\app\src\main\java\com\example\versesapp\ui\Composables.kt
package com.example.versesapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.versesapp.model.Verse

/**
 * The main screen showing a scrollable list of all verses.
 * This is the default startup view.
 */
@Composable
fun VerseListScreen(
    verses: List<Verse>,
    isLoading: Boolean,
    onVerseClick: (Double) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ಕನ್ನಡ ವಚನಗಳು - Verses App") })
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (isLoading) {
                // Show a loading indicator while the data is being fetched
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (verses.isEmpty()) {
                // Show a message if no verses were loaded
                Text(
                    text = "No verses loaded. Check data files.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // The scroll feature for all verses
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(verses) { verse ->
                        VerseCard(verse = verse, onVerseClick = onVerseClick)
                    }
                }
            }
        }
    }
}

/**
 * Reusable Card component for displaying a single verse summary.
 * Displays only the 4 lines of Kannada Original.
 */
@Composable
fun VerseCard(
    verse: Verse,
    onVerseClick: (Double) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVerseClick(verse.verseNumber) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Chapter and Verse Number
            Text(
                text = "${verse.chapter} (Verse ${verse.verseNumber.toInt()})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(Modifier.height(8.dp))
            
            // Display the 4 lines of Kannada Original text
            // Requirement: "the default has all verses with 4 lines of the verse in kannada"
            verse.kannadaLines.forEach { line ->
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyLarge,
                    // Use line-height adjustment for better readability if needed
                )
            }
        }
    }
}