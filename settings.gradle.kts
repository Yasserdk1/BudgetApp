// settings.gradle.kts (au niveau du projet)
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // AJOUTER CETTE LIGNE POUR MPANDROIDCHART
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "BudgetApp"
include(":app")