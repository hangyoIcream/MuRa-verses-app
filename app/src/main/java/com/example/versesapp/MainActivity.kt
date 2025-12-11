// C:\app\src\main\java\com\example\versesapp\MainActivity.kt
package com.example.versesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.versesapp.data.VerseRepository
import com.example.versesapp.ui.* // Import all your composables
import com.example.versesapp.ui.theme.YourAppTheme // Assuming you have a theme

class MainActivity : ComponentActivity() {

    // Define the repository here, injecting it if using Hilt, 
    // or passing Context directly as a simple setup.
    private val repository by lazy { VerseRepository(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourAppTheme {
                // Provide the repository to the ViewModel factory
                val versesViewModel: VersesViewModel = viewModel(
                    factory = VersesViewModelFactory(repository)
                )
                
                VersesApp(versesViewModel)
            }
        }
    }
}

// Simple factory to create the ViewModel with the repository dependency
// (This is often replaced by Hilt/Koin in production apps)
class VersesViewModelFactory(private val repository: VerseRepository) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VersesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VersesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Top-level Composable for Navigation
@Composable
fun VersesApp(viewModel: VersesViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        
        // 1. VERSE LIST SCREEN (Default Startup View)
        composable("list") {
            // Collect the state from the ViewModel
            val verses by viewModel.verses.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            VerseListScreen(
                verses = verses,
                isLoading = isLoading,
                onVerseClick = { verseNumber ->
                    navController.navigate("detail/$verseNumber")
                }
            )
        }

        // 2. VERSE DETAIL SCREEN
        composable("detail/{verseNumber}") { backStackEntry ->
            val verseNumber = backStackEntry.arguments?.getString("verseNumber")?.toDoubleOrNull()
            
            if (verseNumber != null) {
                val verse = viewModel.getVerseByNumber(verseNumber)
                if (verse != null) {
                    VerseDetailScreen(verse = verse)
                } else {
                    // Handle case where verse is not found (e.g., error message)
                    Text("Verse not found!")
                }
            }
        }
    }
}