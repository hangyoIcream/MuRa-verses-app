package com.example.versesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.versesapp.data.Repository
import com.example.versesapp.ui.VersesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val baseUrl = "https://raw.githubusercontent.com/hangyoIcream/MuRa-verses-app/main/data/json/"
        val repo = Repository(this, baseUrl)

        setContent {
            VersesScreen(repo = repo)
        }
    }
}
