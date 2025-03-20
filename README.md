# Rezeptverwaltungssystem

Eine einfache Anwendung zur Speicherung und Verwaltung von Rezepten


## 🚀 Installation

### 📌 Voraussetzungen
- Java 23+ (Empfohlen OpenJDK 23)
- Apache Maven
- [H2 Database](https://www.h2database.com/)

### 📂 Datenbankeinrichtung

Das Projekt verwendet **SQLite** als Datenbank. Die Datenbankdatei befindet sich im Repository unter:

```plaintext
rezeptverwaltungsdb.db
```
### 📂 Projekt klonen & starten
- Repository klonen
```sh
  git clone https://github.com/SmallYhorm/Prog3Projekt.git
```
- Abhängigkeiten installieren
```sh
  cd Prog3Projekt
  mvn clean install
```
- Starte die Anwendung

  
```sh
   mvn exec:java -Dexec.mainClass="Skeleton"
```



## Nutzung

**Speichern eines neuen Rezeptes**

Fülle die Felder **ID, Name, etc** unten aus und klicke auf den Button **"Add Recipe"**
![Speichern eines Rezeptes](images/screenshot1.png)


