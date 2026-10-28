# COS 201: Student Course Management System (Java)

**MIVA Open University** — Mid-Semester Lab Assessment Project  
**Author:** Moses Abutu Audu  
**Matriculation Number:** 2025/A/CSC/0179  
**Email:** m.abutu3785@miva.edu.ng  
**Department:** B.Sc. Computer Science  
**Course Code & Title:** COS 201 — Programming I  

---

## Project Overview
The **Student Course Management System** is a modular, console-based Java application built to manage course enrollments, compute semester credit loads, enable search operations, and persist data using local text files (`courses.txt`). 

The application is structured cleanly into two main components:
1. `Course.java`: Model class encapsulating course attributes (Code, Title, Credit Units) and file format conversions.
2. `CourseManagementSystem.java`: Core application controller handling user interaction, recursive calculations, search routines, exception handling, and file persistence.

---

## 🚀 Key Features & Operations

| Option | Operation | Implementation Highlights |
| :---: | :--- | :--- |
| **1** | **Add Course** | Prompts for course details with input sanitization (case normalization, string trimming, positive unit validation). |
| **2** | **View All Courses** | Displays all currently registered courses in a clean tabular format. |
| **3** | **Search Course** | Performs a case-insensitive search by course code using a **recursive linear search**. |
| **4** | **Compute Total Units** | Recursively sums the credit units across the internal list without standard loops. |
| **5** | **Save to File** | Persists current course records to `courses.txt` using pipe-delimited formatting (`|`). |
| **6** | **Load from File** | Parses and loads existing course data from `courses.txt` with defensive array boundary checking. |
| **7** | **Exit** | Terminates the interactive session. |

---

## Technical Highlights

### 1. Applied Recursion
Recursion is integrated into three core areas:
- **Recursive Menu Execution (`runMenuLoop`)**: Manages continuous application flow recursively.
- **Recursive Unit Summation (`calculateTotalUnitsRecursive`)**: Traverses the list index recursively (`index - 1`) down to base case `index < 0` to compute credit load.
- **Recursive Search (`recursiveSearch`)**: Checks course code matches sequentially until found or reaching index bound (`index >= list.size()`).

### 2. Exception & Input Safety
- **Buffer Cleansing**: Standardized on `scanner.nextLine()` with integer parsing (`Integer.parseInt`) to eliminate newline buffer traps.
- **Input Validation**: Custom checking via `IllegalArgumentException` prevents invalid, blank, or negative values.
- **File I/O Defense**: Disk reads wrap record parsing in `try-catch` blocks to skip malformed lines without crashing.

---

## 💻 Compilation & Execution Instructions

### Prerequisites
- JDK 8 or higher installed on your system.

### Build and Run via Command Line (Linux / Fedora / macOS / Windows)

1. **Clone or download** both source files (`Course.java` and `CourseManagementSystem.java`) into the same directory:
   ```bash
   mkdir -p ~/miva/cos201/lab
   cd ~/miva/cos201/lab
   ```

2. **Compile the source files**:
   ```bash
   javac Course.java CourseManagementSystem.java
   ```

3. **Run the application**:
   ```bash
   java CourseManagementSystem
   ```

---

##  File Structure

```
├── Course.java                 # Model class representing individual courses
├── CourseManagementSystem.java  # Main application logic & menu loop
├── courses.txt                 # Data file generated for persistent storage
└── README.md                   # Project documentation
```

---

## 📄 License & Attribution
Submitted as part of the COS 201 Mid-Semester Lab Assessment at **MIVA Open University**.  
Copyright © 2026 Moses Abutu Audu. All Rights Reserved.
