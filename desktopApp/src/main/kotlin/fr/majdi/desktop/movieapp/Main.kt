package fr.majdi.desktop.movieapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.majdi.moviesapp.di.initKoin
import fr.majdi.moviesapp.presentation.ui.screens.MainApp

fun main() = application {
    // Initialisation de Koin (injection de dépendances)
    initKoin()
    // Fenêtre principale de l'application
    Window(
        onCloseRequest = ::exitApplication,
        title = "Movies App"
    ) {
        MainApp()
    }
}
