# COS 201: Student Course Management System (Java)

**MIVA Open University** — Mid-Semester Lab Assessment Project  
**Course Code & Title:** COS 201 — Programming I  
**Department:** B.Sc. Computer Science

---

## Project Overview

The **Student Course Management System** manages a list of courses: adding records,
viewing them, searching by course code, computing the total credit load, and saving/loading
data to disk. The repository contains a **baseline implementation on `main`** plus **three
contributor implementations**, all built around the same seven menu operations:

| Branch | Implementation | UI | Storage | Author |
| :--- | :--- | :--- | :--- | :--- |
| `main` | Baseline — console application | Terminal (`java.util.Scanner`) | Pipe-delimited text (`courses.txt`) | Moses Abutu Audu |
| `Samuel` | Logic 1 — console application | Terminal (`java.util.Scanner`) | Java serialization (`courses_logic1.dat`) | Samuel |
| `Borah` | Logic 2 — console application | Terminal (`java.util.Scanner`) | CSV (`courses_logic2.csv`) | Borah |
| `Daniel’s` | Logic 3 — GUI application | `javax.swing.JOptionPane` dialogs | Binary stream (`courses_logic3.bin`) | Daniel |

All implementations use recursion and persist data locally, but they differ in how they
interact with the user and how records are stored. The sections below document each
implementation so any of them can be built and run independently.

**Printable report:** [`COS201_SCMS_Report_Daniel.odt`](COS201_SCMS_Report_Daniel.odt) — a
formatted Miriam-style mid-semester lab report for this implementation.

---

## Shared Operations

Every implementation exposes the same seven operations:

| Option | Operation | Description |
| :---: | :--- | :--- |
| **1** | Add Course | Captures course code, title and credit units. |
| **2** | View All Courses | Lists every course currently held in memory. |
| **3** | Search Course | Finds a course by code (case-insensitive). |
| **4** | Compute Total Units | Sums the credit units of all courses. |
| **5** | Save to File | Writes the current course list to disk. |
| **6** | Load from File | Restores the course list from disk. |
| **7** | Exit | Ends the session. |

---

## Baseline Implementation — Console Application (`main` branch)

### Components
- `src/Course.java` — `Serializable` model holding `courseCode`, `courseTitle` and
  `creditUnits`. Setters validate input (no blank code/title, positive units), normalize the
  code to uppercase, and sanitize `|` out of titles. `toFileFormat()` / `fromFileFormat()`
  convert records to and from the pipe-delimited storage format.
- `src/CourseManagementSystem.java` — entry point, menu loop, recursive search, recursive unit
  summation and file persistence.

### Behaviour
- **Menu:** iterative `while` loop driven by `Scanner`; the user presses ENTER to return to the
  menu after each operation.
- **Validation:** the model setters throw `IllegalArgumentException` for blank codes/titles,
  non-positive units, or titles made only of delimiter characters.
- **Search:** `recursiveSearch(targetCode, index)` performs a case-insensitive recursive linear
  search (base cases: end of list → `null`, matching code → the course).
- **Recursion:** `calculateTotalUnitsRecursive(index)` sums units from the end of the list down
  to index `0` (base case: `index < 0`).
- **Persistence:** records are written to **`courses.txt`** in `code|title|units` form with
  `PrintWriter` and read back with `BufferedReader`. Existing records are auto-loaded silently
  at startup.

### Build & Run

```bash
git checkout main
cd src
javac Course.java CourseManagementSystem.java
java CourseManagementSystem
```

> Run from `src/` so that `courses.txt` is created in the working directory.

---

## Implementation 1 — Console Application (`Samuel` branch)

### Components
- `src/CourseManagementSystem.java` — contains both:
  - `Course` — `Serializable` model holding `code`, `title` and `units`. The constructor
    trims the title and normalizes the code to uppercase.
  - `CourseManagementSystem` — entry point, menu loop, search, recursion and file persistence.

### Behaviour
- **Menu:** iterative `while` loop driven by `Scanner`.
- **Input safety:** every read uses `scanner.nextLine()` followed by
  `Integer.parseInt(...)`, so newline-buffer issues are avoided and bad numbers raise a
  handled `NumberFormatException`.
- **Search:** linear scan with a case-insensitive code comparison.
- **Recursion:** `computeUnitsRecursive(index)` sums credit units from the end of the list
  down to index `0` (base case: `index >= courses.size()`).
- **Persistence:** Java object serialization — the whole `ArrayList<Course>` is written with
  `ObjectOutputStream` to **`courses_logic1.dat`** and read back with `ObjectInputStream`.

### Build & Run

```bash
git checkout Samuel
cd src
javac CourseManagementSystem.java
java CourseManagementSystem
```

Or, with JDK 11+ single-file source execution (no separate compile step):

```bash
cd src
java CourseManagementSystem.java
```

> Run from `src/` so that `courses_logic1.dat` is created in the working directory.

---

## Implementation 2 — Console Application (`Borah` branch)

### Components
- `src/SCMS_Logic2.java` — contains both:
  - `CourseData` — model holding `code`, `title` and `units` (code trimmed and uppercased,
    title trimmed).
  - `SCMS_Logic2` — entry point and menu logic.

### Behaviour
- **Storage model:** courses are held in a `HashMap<String, CourseData>` keyed by course code,
  so adding a duplicate code replaces the existing record.
- **Menu:** iterative `while` loop driven by `Scanner`.
- **Input safety:** the option and units inputs are parsed with `Integer.parseInt`, and bad
  numbers are caught and reported.
- **Search:** the map values are copied into an array, then `recursiveSearch(arr, target, index)`
  scans it (base cases: index out of bounds → `null`; code match → the record).
- **Total units:** iterative sum over the map values.
- **Persistence:** CSV — one `code,title,units` line per course, written with `PrintWriter` and
  read with `BufferedReader` from **`courses_logic2.csv`**.
- **Load safety:** blank lines are ignored; lines with the wrong number of fields, an empty
  course code, or non-numeric units are reported and skipped. If the file is missing, the
  in-memory list is left unchanged.

### Build & Run

```bash
git checkout Borah
cd src
javac SCMS_Logic2.java
java SCMS_Logic2
```

Or, with JDK 11+ single-file source execution:

```bash
cd src
java SCMS_Logic2.java
```

> Run from `src/` so that `courses_logic2.csv` is created in the working directory.

---

## Implementation 3 — GUI Application (`Daniel’s` branch)

### Components
- `src/Course.java` — contains both:
  - `CourseObj` — lightweight model holding `code`, `title` and `units` (code normalized to
    uppercase, title trimmed).
  - `Course` — entry point and GUI logic using `JOptionPane` dialogs.

### Behaviour
- **Menu:** a single input dialog is shown for the seven options.
- **Recursion:** `recursiveMenu()` processes one choice, then calls itself to redraw the menu.
  The base case is reached when the user cancels the dialog or selects **7 (Exit)**.
- **Search & units:** straightforward iterative loops over the in-memory list.
- **Persistence:** `DataOutputStream` / `DataInputStream` write and read the record count and
  each field as binary data in **`courses_logic3.bin`**.
- **Requirements:** a graphical desktop session is required (Swing dialogs will not run in a
  headless environment).

### Build & Run

```bash
git checkout "Daniel’s"
javac -d out src/Course.java
java -cp out Course
```

> The branch ships stale `src/*.class` files. Compiling to `out/` (as above) keeps the build
> clean and avoids running an outdated class.

---

## Repository Layout

`main` branch — baseline console application:

```text
COS-201/
├── README.md
├── .gitignore
├── courses.txt                     # Pipe-delimited course data
└── src/
    ├── Course.java                 # Serializable course model
    └── CourseManagementSystem.java # Console application
```

`Samuel` branch — console application:

```text
COS-201/
├── README.md                       # This document
├── .gitignore
├── courses.txt                     # Legacy sample data (courses.txt-based version)
└── src/
    └── CourseManagementSystem.java # Course model + console application
```

`Borah` branch — console application:

```text
COS-201/
├── README.md
├── .gitignore
├── courses.txt
└── src/
    └── SCMS_Logic2.java            # CourseData model + HashMap/CSV console application
```

`Daniel’s` branch — GUI application:

```text
COS-201/
├── README.md
├── .gitignore
├── courses.txt
└── src/
    └── Course.java                 # CourseObj model + JOptionPane GUI application
```

Generated at runtime (not committed):

| File | Branch | Format |
| :--- | :--- | :--- |
| `courses.txt` | `main` | Pipe-delimited text (`code\|title\|units`) |
| `courses_logic1.dat` | `Samuel` | Serialized `ArrayList<Course>` |
| `courses_logic2.csv` | `Borah` | Comma-separated text |
| `courses_logic3.bin` | `Daniel’s` | Binary record stream |

---

## Requirements

- **JDK 8+** for `javac` / `java` builds.
- **JDK 11+** if you want to use the `java FileName.java` single-file launcher.
- The GUI branch additionally needs a display server (X11/Wayland or Windows/macOS desktop).

---

## License & Attribution

Submitted as part of the COS 201 Mid-Semester Lab Assessment at **MIVA Open University**.

- Baseline console implementation (`main`) — **Moses Abutu Audu**
- Console implementation (Logic 1) — **Samuel**
- Console implementation (Logic 2) — **Borah**
- GUI implementation (Logic 3) — **Daniel**

Copyright © 2026. All Rights Reserved.
