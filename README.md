# Rezeptverwaltungssystem

Eine einfache Anwendung zur Speicherung und Verwaltung von Rezepten


## 🚀 Installation

### 📌 Voraussetzungen
- Java 23+ (Empfohlen OpenJDK 23)
- Apache Maven
- [H2 Database](https://www.h2database.com/)

### 🛢️ Datenbankeinrichtung

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



## 🍴📋 Nutzung

### ➕🍲 Hinzufügen eines neuen Rezepts

Um ein neues Rezept hinzuzufügen, kannst du das folgende Formular verwenden:

- **ID**: Die ID des Rezepts
- **Name**: Der Name des Rezepts
- **Cuisine**: Der Küche des Rezepts
- **Category**: Die Kategorie des Rezepts
- **Instructions**: Eine Schritt-für-Schritt-Beschreibung der Zubereitung
- **Nutrition**: Die Nährwerte des Rezepts
- **Cooking Time**: Die Zubereitungszeit 
- **Ingredients**: Die Zutaten des Rezepts

**Beispiel**:

Gib in der GUI die folgenden Informationen ein:

- **ID**: 1
- **Name**: "Spaghetti Bolognese"
- **Cuisine**: "Italienisch"
- **Category**: "Hauptgericht"
- **Instructions**: "Zwiebel anbraten, Hackfleisch hinzufügen, Tomaten und Gewürze dazugeben. Mit Spaghetti servieren."
- **Nutrition**: 400 (in kcal)
- **Cooking Time**: "30 Minuten"
- **Ingredients**: "500g Rinderhack, 1 Zwiebel, 1 Dose Tomaten, 200g Spaghetti"

**Screenshot des Formulars**:

![Rezept hinzufügen Form](images/screenshot8.png)

---

### ❌🗑️ Rezept löschen

Um ein Rezept zu löschen, gib die **ID** des Rezeptes im Formular unten ein und klicke auf **"Delete Recipe"**.

**Beispiel: Das Rezept mit der ID 1 wird gelöscht.**

![Rezept löschen Form](images/screenshot5.png)

---

### 🔄✏️ Aktualisieren eines Rezepts

Um ein bestehendes Rezept zu aktualisieren, gib die ID des Rezepts im Formular an, fülle die gewünschten Felder aus und klicke auf **"Update Recipe"**. 

**Beispiel: Die Küche des Rezept mit der ID 1 wird geändert zu "Italienisch"**

![Rezept aktualisieren Form](images/screenshot6.png)

---

### 🔍📂 Filtern von Rezepten

Um Rezepte zu filtern, kannst du nach Kriterien suchen, wie z.B. **Name**, **Küche** oder **Kategorie**. Fülle dazu das gewünschte Feld unten im Formular aus und klicke auf **"Filter Recipes"**.

**Beispiel: Alle Rezepte aus der italienischen Küche werden gefiltert.**

![Rezept filtern Form](images/screenshot7.png)

---

### 📜📤 Alle Rezepte laden

Um alle Rezepte anzuzeigen, klicke auf **"Load Recipes"**. Alle gespeicherten Rezepte werden in der Liste angezeigt.

---

### 🗑️🧹 Alle Rezepte löschen

Um alle Rezepte auf einmal zu löschen, klicke auf **"Delete all Recipes"**. Achte darauf, dass dieser Vorgang nicht rückgängig gemacht werden kann!

---








