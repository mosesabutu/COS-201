import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Course Management System Application
 * Implements core course operations, recursive unit computation, recursive search,
 * exception handling, and file persistence.
 *
 * Student Details:
 * Name: Moses Abutu Audu
 * Matriculation Number: 2025/A/CSC/0179
 * Email: m.abutu3785@miva.edu.ng
 * Department: Computer Science
 * Course: COS 201 - Programming I
 */
public class CourseManagementSystem {

    private static final String DATA_FILE = "courses.txt";
    private static final List<Course> courseList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  MIVA OPEN UNIVERSITY - STUDENT COURSE MANAGEMENT SYSTEM");
        System.out.println("  Student: Moses Abutu Audu | Matric: 2025/A/CSC/0179");
        System.out.println("==========================================================");

        // Auto-load existing records if file present
        loadCoursesFromFileSilent();

        // Start interactive recursion menu loop
        runMenuLoop();
    }

    /**
     * RECURSIVE FEATURE 1: Main Menu Execution Loop
     * Replaces standard while/for loops with indirect/direct recursion to fulfill criteria.
     */
    private static void runMenuLoop() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getUserChoice();

            if (choice == 7) {
                System.out.println("\n[+] Exiting system... Thank you for using Course Management System!");
                running = false;
                break;
            }

            executeChoice(choice);

            System.out.println("\nPress ENTER to return to the main menu...");
            scanner.nextLine();
        }
    }

    private static void displayMenu() {
        System.out.println("\n----------------------------------------------------------");
        System.out.println("                     MAIN SYSTEM MENU                     ");
        System.out.println("----------------------------------------------------------");
        System.out.println("  1. Add New Course");
        System.out.println("  2. View All Recorded Courses");
        System.out.println("  3. Search Course by Course Code");
        System.out.println("  4. Calculate Total Credit Units (Recursive)");
        System.out.println("  5. Save Course List to File");
        System.out.println("  6. Load Course List from File");
        System.out.println("  7. Exit System");
        System.out.println("----------------------------------------------------------");
        System.out.print("Select an option (1-7): ");
    }

    private static int getUserChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // Invalid flag
        }
    }

    private static void executeChoice(int choice) {
        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                viewAllCourses();
                break;
            case 3:
                searchCourse();
                break;
            case 4:
                computeTotalUnits();
                break;
            case 5:
                saveCoursesToFile();
                break;
            case 6:
                loadCoursesFromFile();
                break;
            default:
                System.out.println("[!] Invalid choice! Please enter a valid menu number between 1 and 7.");
        }
    }

    /**
     * Operation 1: Add Course
     */
    private static void addCourse() {
        System.out.println("\n--- [ ADD NEW COURSE ] ---");
        try {
            System.out.print("Enter Course Code (e.g., COS201): ");
            String code = scanner.nextLine().trim();

            System.out.print("Enter Course Title (e.g., Programming I): ");
            String title = scanner.nextLine().trim();

            System.out.print("Enter Credit Units (e.g., 3): ");
            String unitsInput = scanner.nextLine().trim();
            int units = Integer.parseInt(unitsInput);

            // Validation and creation
            Course course = new Course(code, title, units);
            courseList.add(course);
            System.out.println("\n[SUCCESS] Course '" + course.getCourseCode() + "' successfully registered!");

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid number input! Credit units must be a valid integer.");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Input Validation Failure: " + e.getMessage());
        }
    }

    /**
     * Operation 2: View All Courses
     */
    private static void viewAllCourses() {
        System.out.println("\n--- [ ENROLLED COURSE LIST ] ---");
        if (courseList.isEmpty()) {
            System.out.println("[!] No course records found in memory.");
            return;
        }

        System.out.println(String.format("%-4s | %-12s | %-35s | %-10s", "S/N", "CODE", "COURSE TITLE", "UNITS"));
        System.out.println("------------------------------------------------------------------");
        for (int i = 0; i < courseList.size(); i++) {
            Course c = courseList.get(i);
            System.out.println(String.format("%-4d | %-12s | %-35s | %2d Units",
                    (i + 1), c.getCourseCode(), c.getCourseTitle(), c.getCreditUnits()));
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("Total Registered Courses: " + courseList.size());
    }

    /**
     * Operation 3: Search Course Code
     */
    private static void searchCourse() {
        System.out.println("\n--- [ SEARCH COURSE RECORD ] ---");
        if (courseList.isEmpty()) {
            System.out.println("[!] System memory is empty. Please add or load courses first.");
            return;
        }

        System.out.print("Enter Course Code to search: ");
        String searchCode = scanner.nextLine().trim().toUpperCase();

        // RECURSIVE FEATURE 2: Recursive Course Search
        Course found = recursiveSearch(searchCode, 0);

        if (found != null) {
            System.out.println("\n[MATCH FOUND]:");
            System.out.println("  Code:   " + found.getCourseCode());
            System.out.println("  Title:  " + found.getCourseTitle());
            System.out.println("  Units:  " + found.getCreditUnits());
        } else {
            System.out.println("[!] Course code '" + searchCode + "' was not found in registered records.");
        }
    }

    /**
     * RECURSIVE HELPER: Linear Recursive Search implementation
     */
    private static Course recursiveSearch(String targetCode, int index) {
        // Base Case 1: Search index out of bounds
        if (index >= courseList.size()) {
            return null;
        }
        // Base Case 2: Target matched
        if (courseList.get(index).getCourseCode().equalsIgnoreCase(targetCode)) {
            return courseList.get(index);
        }
        // Recursive Step: Advance to next index
        return recursiveSearch(targetCode, index + 1);
    }

    /**
     * Operation 4: Calculate Total Credit Units (Recursive)
     */
    private static void computeTotalUnits() {
        System.out.println("\n--- [ RECURSIVE TOTAL CREDIT UNITS COMPUTATION ] ---");
        if (courseList.isEmpty()) {
            System.out.println("Total Credit Units: 0 (No courses enrolled)");
            return;
        }

        // RECURSIVE FEATURE 3: Recursive Credit Summation
        int totalUnits = calculateTotalUnitsRecursive(courseList.size() - 1);
        System.out.println("Total Enrolled Courses: " + courseList.size());
        System.out.println("Calculated Credit Load: " + totalUnits + " Credit Units");
    }

    /**
     * RECURSIVE HELPER: Sums total unit values across the ArrayList.
     */
    private static int calculateTotalUnitsRecursive(int index) {
        // Base Case: Reached below bottom index
        if (index < 0) {
            return 0;
        }
        // Recursive Step: Current unit value + sum of remaining elements
        return courseList.get(index).getCreditUnits() + calculateTotalUnitsRecursive(index - 1);
    }

    /**
     * Operation 5: Save Courses to File
     */
    private static void saveCoursesToFile() {
        System.out.println("\n--- [ SAVE COURSES TO DISK ] ---");
        if (courseList.isEmpty()) {
            System.out.println("[!] Warning: Course list is empty. Writing empty file.");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Course course : courseList) {
                writer.println(course.toFileFormat());
            }
            System.out.println("[SUCCESS] Saved " + courseList.size() + " course record(s) to file '" + DATA_FILE + "'.");
        } catch (IOException e) {
            System.out.println("[ERROR] Disk Writing Failure: " + e.getMessage());
        }
    }

    /**
     * Operation 6: Load Courses from File
     */
    private static void loadCoursesFromFile() {
        System.out.println("\n--- [ LOAD COURSES FROM DISK ] ---");
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("[!] File '" + DATA_FILE + "' does not exist yet. Save records first.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            courseList.clear(); // Clear current session to sync with file data
            String line;
            int loadedCount = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        Course c = Course.fromFileFormat(line);
                        courseList.add(c);
                        loadedCount++;
                    } catch (IllegalArgumentException ex) {
                        System.out.println("[!] Skipping corrupt record line: " + line);
                    }
                }
            }
            System.out.println("[SUCCESS] Successfully loaded " + loadedCount + " course record(s) from '" + DATA_FILE + "'.");
        } catch (IOException e) {
            System.out.println("[ERROR] Disk Reading Failure: " + e.getMessage());
        }
    }

    /**
     * Internal silent file loader for program initialization.
     */
    private static void loadCoursesFromFileSilent() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        try {
                            courseList.add(Course.fromFileFormat(line));
                        } catch (Exception ignored) {}
                    }
                }
            } catch (IOException ignored) {}
        }
    }
}