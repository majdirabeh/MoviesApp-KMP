package fr.majdi.moviesapp

import androidx.compose.ui.window.ComposeUIViewController
import fr.majdi.moviesapp.presentation.ui.screens.MainApp
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { MainApp() }
}