package fr.majdi.moviesapp.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowBack
import fr.majdi.moviesapp.presentation.ui.navigation.AppNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryAsState()
    val title = when (currentRoute?.destination?.route) {
        "movie_list" -> "List Movies"
        "movie_detail/{movieId}" -> "Detail"
        else -> "Movies"
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        if (currentRoute?.destination?.route != "movie_list") {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = TablerIcons.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}