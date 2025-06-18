# EMX to JSON Converter (Java 21)

Ce projet permet de convertir un fichier `.emx` (format XMI UML) en JSON, à l’aide de Java 21 et Jackson.  
Il est utile pour transformer des modèles UML générés par des outils comme Enterprise Architect, IBM Rational, etc., en structures JSON exploitables.

## ✅ Prérequis
- Java 21
- Maven 3.9+
- (Facultatif) IDE comme IntelliJ ou VS Code

## ⚙️ Dépendances
- `jackson-databind` pour la conversion Java → JSON

## 🚀 Utilisation
1. Placez votre fichier `modele.emx` à la racine du projet.
2. Compilez et exécutez :
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.example.emx.EmxToJsonConverter"
