package com.example.alwa.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alwa.ui.navigation.AlwaNavGraph
import com.example.alwa.ui.theme.AlwaTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlwaApp(viewModel)
        }
    }
}

@Composable
fun AlwaApp(viewModel: AppViewModel) {
    AlwaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AlwaNavGraph(
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel
            )
        }
    }
}