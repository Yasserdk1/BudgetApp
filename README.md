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
