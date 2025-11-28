package com.example.versesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.versesapp.data.Repository
import com.example.versesapp.model.VerseLine
import kotlinx.coroutines.launch

@Composable
fun VersesScreen(repo: Repository) {
    var verseNumText by remember { mutableStateOf("") }
    var verseLines by remember { mutableStateOf<List<VerseLine>?>(null) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Row {
            OutlinedTextField(value = verseNumText, onValueChange = { verseNumText = it }, label = { Text("Verse #") }, modifier = Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                val num = verseNumText.toIntOrNull() ?: return@Button
                scope.launch {
                    verseLines = repo.fetchVerse(num)
                }
            }) { Text("Load") }
        }

        Spacer(Modifier.height(12.dp))

        if (verseLines == null) Text("No verse loaded.") else VerseLinesList(verseLines!!)
    }
}

@Composable
fun VerseLinesList(lines: List<VerseLine>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(lines) { line ->
            Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "${'$'}{line.line_number?.toInt() ?: 0}. ${'$'}{line.kannada_original ?: "-"}")
                    if (!line.english_translation.isNullOrBlank()) Text(text = line.english_translation ?: "", style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}
