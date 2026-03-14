# Compilation du mod Cheat Client

## Prérequis
- **JDK 8** (pas JRE) - https://adoptium.net/temurin/releases/?version=8
- JAVA_HOME doit pointer vers le JDK 8

## Compilation
```bash
gradlew build
```
**Premier lancement :** peut prendre 5-15 minutes (téléchargement et décompilation de Minecraft).
Le JAR sera dans `build/libs/cheatclient-1.0.jar`

## En cas d'erreur
- **"Could not find forge"** : supprimer le dossier `C:\Users\luisg\.gradle\caches\forge_gradle` puis relancer
- **"tools.jar"** : utilise le JDK 8 (pas JRE), et définis JAVA_HOME correctement
- **Blocage** : fermer tout (Cursor, Minecraft), supprimer le dossier `build` du projet, relancer

## Installation
Copier `cheatclient-1.0.jar` dans le dossier `mods` de Minecraft Forge 1.12.2.

## Utilisation
- **INSERT** : Ouvrir/fermer le menu
- Clic gauche sur un module : activer/désactiver

## Modules
- **Movement** : Fly, Noclip
- **Render** : ESP (entités), Xray (minerais)
- **Combat** : Autoclick, KillAura (PVP)
