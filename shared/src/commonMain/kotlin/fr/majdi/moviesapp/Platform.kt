package fr.majdi.moviesapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform