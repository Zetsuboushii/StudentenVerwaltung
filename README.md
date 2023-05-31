# StudentenVerwaltung
Dieses Projekt wird im Rahmen der Java Vorlesung im 2. Semester an der DHBW Mannheim durchgeführt.


## Datenbankstruktur
### Student
- matrikelnummer (pk)
- vname, name
- kursname (fk)
- firma
- vorkenntnisse in java (als skala, kein freitext)

### Kurs
- kursname (pk)
- raum (nur ein raum pro kurs)


-> soll persistent sein (auch nach schließen noch da)