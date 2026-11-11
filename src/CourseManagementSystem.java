import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Course implements Serializable {
    private String code;
    private String title;
    private int units;

    public Course(String code, String title, int units) {
        this.code = code.trim().toUpperCase(); // String processing
        this.title = title.trim();
        this.units = units;
    }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getUnits() { return units; }
    
    @Override
    public String toString() {
        return "Code: " + code + " | Title: " + title + " | Units: " + units;
    }
}

public class CourseManagementSystem {
    private static ArrayList<Course> courses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "courses_logic1.dat";

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Student Course Management (Logic 1) ---");
            System.out.println("1. Add Course\n2. View All Courses\n3. Search Course by Code");
            System.out.println("4. Compute Total Units\n5. Save to File\n6. Load from File\n7. Exit");
            System.out.print("Select option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1: addCourse(); break;
                    case 2: viewCourses(); break;
                    case 3: searchCourse(); break;
                    case 4: 
                        System.out.println("Total Units: " + computeUnitsRecursive(0)); 
                        break;
                    case 5: saveToFile(); break;
                    case 6: loadFromFile(); break;
                    case 7: exit = true; System.out.println("Exiting..."); break;
                    default: System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    private static void addCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        try {
            System.out.print("Enter Units: ");
            int units = Integer.parseInt(scanner.nextLine().trim());
            courses.add(new Course(code, title, units));
            System.out.println("Course Added!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Units must be an integer.");
        }
    }

    private static void viewCourses() {
        if (courses.isEmpty()) { System.out.println("No courses found."); return; }
        for (Course c : courses) System.out.println(c);
    }

    private static void searchCourse() {
        System.out.print("Enter Course Code to Search: ");
        String target = scanner.nextLine().trim().toUpperCase();
        for (Course c : courses) {
            if (c.getCode().equals(target)) {
                System.out.println("Found: " + c);
                return;
            }
        }
        System.out.println("Course not found.");
    }

    // RECURSION APPLIED HERE
    private static int computeUnitsRecursive(int index) {
        if (index >= courses.size()) return 0;
        return courses.get(index).getUnits() + computeUnitsRecursive(index + 1);
    }

    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(courses);
            System.out.println("Saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            courses = (ArrayList<Course>) ois.readObject();
            System.out.println("Loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
