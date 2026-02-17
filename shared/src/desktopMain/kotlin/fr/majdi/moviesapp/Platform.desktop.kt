package fr.majdi.moviesapp

class DesktopPlatform : Platform {
    override val name: String = "Desktop"
}

actual fun getPlatform(): Platform = DesktopPlatform()