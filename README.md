# 💰 BudgetApp - Gestionnaire de Dépenses

![Version](https://img.shields.io/badge/version-1.0-blue)
![Platform](https://img.shields.io/badge/platform-Android-brightgreen)
![Language](https://img.shields.io/badge/language-Java-orange)

## 📱 **Présentation du Projet**

**BudgetApp** est une application Android complète de **suivi de dépenses et gestion de budget**, développée dans le cadre du cours de Programmation Mobile. Elle permet aux utilisateurs d'enregistrer, catégoriser et visualiser leurs dépenses quotidiennes pour mieux maîtriser leur budget.

### 🎯 **Objectifs Pédagogiques**

Ce projet démontre la maîtrise de tous les concepts fondamentaux du développement Android :
- ✅ Multiple Activities avec Intents
- ✅ RecyclerView + Adapter personnalisé
- ✅ SQLite + CRUD complet
- ✅ SharedPreferences (thème, login)
- ✅ UI soignée avec ConstraintLayout
- ✅ Listeners et findViewById
- ✅ Ressources externalisées

---

## ✨ **Fonctionnalités Détaillées**

### 🔐 **Authentification**
- Écran de connexion avec validation
- Identifiants de démo : `user` / `password`
- Option "Se souvenir de moi" (SharedPreferences)
- Redirection automatique si déjà connecté

### 📊 **Dashboard Principal**
- **Cartes statistiques** avec :
  - Total des dépenses
  - Budget mensuel (modifiable)
  - Reste à dépenser
- **Barre de progression** visuelle du budget
- **Liste des dépenses** (RecyclerView) avec :
  - Affichage par date décroissante
  - Couleur différente par catégorie
  - Clic simple → modifier
  - Clic long → supprimer
- **Floating Action Button** (+) pour ajouter

### ➕ **Gestion des Dépenses (CRUD Complet)**
- **Create** : Formulaire d'ajout avec :
  - Montant (validation)
  - Description
  - Catégorie (Spinner)
  - Méthode de paiement
  - Date (DatePicker)
- **Read** : Affichage en liste avec détails
- **Update** : Modification avec pré-remplissage
- **Delete** : Confirmation par dialogue

### 📈 **Statistiques (Bonus)**
- Graphique circulaire (PieChart) avec MPAndroidChart
- Répartition des dépenses par catégorie
- Total général affiché

### 🎨 **Personnalisation**
- **Thème clair/sombre** configurable
- Design Material Design
- Animations fluides
- Messages Toast feedback
- Dialog de confirmation

---

## 🏗️ **Architecture Technique**
com.example.budgetapp/
├── activities/ # Toutes les Activities
│ ├── LoginActivity.java
│ ├── MainActivity.java
│ ├── AddExpenseActivity.java
│ └── StatisticsActivity.java
├── adapters/ # Adapters pour RecyclerView
│ └── ExpenseAdapter.java
├── database/ # Gestion BDD
│ ├── DatabaseHelper.java
│ ├── ExpenseDAO.java
│ └── CategoryDAO.java
├── models/ # POJOs
│ ├── Category.java
│ └── Expense.java
└── utils/ # Utilitaires
└── PreferencesManager.java

### 🗄️ **Base de Données SQLite**

**Tables :**
```sql
categories (id, name, color, icon)
expenses (id, amount, category_id, date, description, payment_method)

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.recyclerview:recyclerview:1.3.0'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
}

🚀 Installation et Utilisation
Prérequis
Android Studio (dernière version recommandée)

JDK 11 ou supérieur

SDK Android (min API 24)

Étapes d'installation
Cloner le dépôt

bash
git clone https://github.com/Yasserdk1/BudgetApp.git
Ouvrir avec Android Studio

File → Open → Sélectionner le dossier BudgetApp

Laisser Gradle synchroniser

Attendre le téléchargement des dépendances

Lancer l'application

Choisir un émulateur (API 24+) ou un appareil physique

Cliquer sur Run ▶️

🔑 Identifiants de démo

Utilisateur : user
Mot de passe : password


💡 Fonctionnalités Bonus
✅ Graphiques avec MPAndroidChart

✅ Données de test automatiques au premier lancement

✅ Message personnalisé quand la liste est vide

✅ Animations sur les items

✅ Barre de progression du budget
