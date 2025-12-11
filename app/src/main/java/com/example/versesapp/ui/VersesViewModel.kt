// C:\app\src\main\java\com\example\versesapp\ui\VersesViewModel.kt
package com.example.versesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.versesapp.data.VerseRepository
import com.example.versesapp.model.Verse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Note: To easily get the Context for the Repository, 
// a dependency injection setup (like Hilt) is best. 
// For simplicity here, we assume the Repository is passed in.
class VersesViewModel(private val repository: VerseRepository) : ViewModel() {

    // MutableStateFlow holds the current list of verses, initialized as empty.
    private val _verses = MutableStateFlow<List<Verse>>(emptyList())
    // StateFlow is the read-only version exposed to the UI.
    val verses: StateFlow<List<Verse>> = _verses.asStateFlow()

    // State to track loading status (optional but recommended)
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadVerses()
    }

    private fun loadVerses() {
        // Use viewModelScope to launch a coroutine that lives as long as the ViewModel
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Since repository.getAllVerses() might do disk IO (reading files), 
                // it should ideally run on a Dispatchers.IO, but for simplicity, 
                // we keep it here. The Repository must be fast or internally use IO dispatcher.
                val loadedVerses = repository.getAllVerses()
                _verses.value = loadedVerses
            } catch (e: Exception) {
                // Handle error case (e.g., show a toast or error screen)
                println("Error loading verses: ${e.message}")
                _verses.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    // Function to get a single verse for navigation to the detail screen
    fun getVerseByNumber(verseNumber: Double): Verse? {
        return _verses.value.find { it.verseNumber == verseNumber }
    }
}