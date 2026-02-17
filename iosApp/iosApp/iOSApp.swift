import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        // Initialisation de Koin (la fonction est exposée par iosMain dans le Shared Module)
        KoinIOSKt.startKoin()
    }
    var body: some Scene {
        WindowGroup {
            ComposeView()
        }
    }
}