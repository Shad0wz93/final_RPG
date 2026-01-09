# RPG – Projet Java Console

##  Présentation

Ce projet est une application Java en console simulant un jeu de rôle (RPG).  
Il permet de créer des personnages, de les regrouper en groupes puis en armées, et de les faire s’affronter dans différents types de combats.

---

##  Fonctionnalités

### 👤 Gestion des personnages
- Création de personnages avec :
  - Force
  - Intelligence
  - Agilité
- Limitation du nombre total de points
- Ajout et suppression de capacités :
  - Invisibilité
  - Esquive
- Validation des données
- Sauvegarde en base de données

---

### 👥 Groupes
- Création de groupes
- Ajout de personnages à un groupe
- Affichage détaillé des groupes et de leurs membres
- Sauvegarde et chargement depuis la base de données

---

### 🛡️ Armées
- Création d’armées
- Ajout de groupes à une armée
- Affichage détaillé des armées (groupes et personnages)
- Sauvegarde et chargement depuis la base de données

---

##  Système de combat

### Types de combats
- Personnage vs Personnage
- Groupe vs Groupe
- Armée vs Armée

### Règles de calcul
- La statistique la plus élevée d’un personnage est considérée comme **statistique forte**
- La statistique forte bénéficie d’un **bonus ×1.5**
- Un **lancer de dé** est effectué par personnage
  - Le dé est ajouté à une statistique différente de la statistique forte
- Pour les groupes et les armées :
  - Les dés sont lancés automatiquement pour chaque personnage
  - La puissance totale est la somme des puissances individuelles

### Capacités
- Certaines capacités peuvent influencer le déroulement du combat
- Exemple :
  - Esquive
  - Invisibilité (possibilité d’un nouveau tour)

---

###  Détails affichés lors d’un combat
- Participants
- Puissance calculée
- Résultat du combat (vainqueur ou match nul)
