package fr.majdi.moviesapp.di

import io.ktor.client.HttpClient

// Fonction attendue : chaque plateforme fournira son propre HttpClient
expect fun httpClient(): HttpClient
