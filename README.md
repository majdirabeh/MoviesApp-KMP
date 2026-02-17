# MoviesApp - Kotlin Multiplatform

Ce projet est une application **Kotlin Multiplatform** qui affiche une liste de films en cours de diffusion ("Now Playing") en utilisant l'API [The Movie Database (TMDb)](https://www.themoviedb.org/).

L'application est conçue pour fonctionner sur **Android**, **iOS** et **Desktop (JVM)**, en partageant la logique métier et l'interface utilisateur grâce à Jetpack Compose.

## 📸 Captures d'écran

List Movies

<img src="https://github.com/majdirabeh/MoviesApp-KMP/blob/master/Screenshots/capture1.png" style=" width:300px ; height:150px " />

Movie Detail

<img src="https://github.com/majdirabeh/MoviesApp-KMP/blob/master/Screenshots/capture2.png" style=" width:300px ; height:150px " />


## ✨ Fonctionnalités

-   Affichage de la liste des films actuellement au cinéma.
-   Affichage de l'écran de détail pour un film sélectionné.
-   Navigation entre les écrans.
-   Architecture simple et modulaire.
-   Interface utilisateur partagée à 100% avec Compose Multiplatform.

## 🛠️ Technologies utilisées

-   **Kotlin Multiplatform** : Partage de code entre différentes plateformes.
-   **Jetpack Compose** : Création d'interfaces utilisateur déclaratives et partagées.
-   **Koin** : Injection de dépendances.
-   **Ktor** : Client réseau pour les appels API.
-   **Kotlinx Serialization** : Parsing des données JSON.
-   **Coil** / **Kamel** : Chargement d'images de manière asynchrone.
-   **Compose Navigation** : Gestion de la navigation dans l'UI.
-   **Architecture MVVM** : Structuration du code de présentation.

## 📂 Structure du projet

Le projet est organisé en plusieurs modules :

-   `./shared` : Contient le code partagé entre toutes les plateformes (Android, iOS, Desktop).
    -   `commonMain` : Logique métier, ViewModels, UI (Composables), et code commun à toutes les cibles.
    -   `androidMain`, `iosMain`, `desktopMain` : Code spécifique à chaque plateforme (ex: initialisation du client Ktor).
-   `./androidApp` : Point d'entrée et configuration de l'application Android.
-   `./iosApp` : Point d'entrée et configuration de l'application iOS (projet Xcode).
-   `./desktopApp` : Point d'entrée de l'application Desktop (JVM).

## 🚀 Comment lancer l'application

### Android

Pour construire et lancer la version de développement de l'application Android, utilisez la configuration de lancement depuis la barre d'outils de votre IDE ou construisez-la directement depuis le terminal :

```shell
./gradlew :androidApp:assembleDebug
```

### iOS

Ouvrez le répertoire `/iosApp` dans Xcode et lancez l'application depuis l'IDE. Assurez-vous d'avoir un simulateur ou un appareil physique sélectionné.

### Desktop (JVM)

Pour construire et lancer la version de développement de l'application de bureau, utilisez la configuration de lancement depuis la barre d'outils de votre IDE ou exécutez-la directement depuis le terminal :

```shell
./gradlew :desktopApp:run
```
