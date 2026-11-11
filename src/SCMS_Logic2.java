import java.io.*;
import java.util.*;

class CourseData {
    String code;
    String title;
    int units;
    
    CourseData(String c, String t, int u) {
        this.code = c.trim().toUpperCase();
        this.title = t.trim();
        this.units = u;
    }
    public String toString() { return code + " - " + title + " (" + units + " units)"; }
}

public class SCMS_Logic2 {
    // Storage via HashMap for rapid access, though we will extract to array for recursion
    private static HashMap<String, CourseData> courseMap = new HashMap<>();
    private static Scanner scan = new Scanner(System.in);
    private static final String CSV_FILE = "courses_logic2.csv";

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n>>> MIVA SCMS (HashMap/CSV Variant) <<<");
            System.out.println("1. Add Course | 2. View All | 3. Search Code");
            System.out.println("4. Total Units| 5. Save(CSV)| 6. Load(CSV) | 7. Exit");
            
            System.out.print("Choice: ");
            String input = scan.nextLine().trim();
            if (input.equals("7")) { System.out.println("Bye."); break; }
            
            try {
                int ch = Integer.parseInt(input);
                executeMenu(ch);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input type. Requires integer.");
            }
        }
    }

    private static void executeMenu(int choice) {
        switch (choice) {
            case 1: add(); break;
            case 2: view(); break;
            case 3: search(); break;
            case 4: compute(); break;
            case 5: saveCSV(); break;
            case 6: loadCSV(); break;
            default: System.out.println("Out of bounds.");
        }
    }

    private static void add() {
        System.out.print("Code: "); String c = scan.nextLine();
        System.out.print("Title: "); String t = scan.nextLine();
        try {
            System.out.print("Units: "); int u = Integer.parseInt(scan.nextLine().trim());
            CourseData course = new CourseData(c, t, u);
            courseMap.put(course.code, course);
            System.out.println("Added.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Unit must be numeric.");
        }
    }

    private static void view() {
        if (courseMap.isEmpty()) { System.out.println("Empty."); return; }
        for (CourseData cd : courseMap.values()) System.out.println(cd);
    }

    private static void search() {
        System.out.print("Code to search: ");
        String target = scan.nextLine().trim().toUpperCase();
        
        // Convert to array to demonstrate recursive search feature
        CourseData[] arr = courseMap.values().toArray(new CourseData[0]);
        CourseData result = recursiveSearch(arr, target, 0);
        
        if (result != null) System.out.println("Found: " + result);
        else System.out.println("Not found.");
    }

    // RECURSION APPLIED HERE
    private static CourseData recursiveSearch(CourseData[] arr, String target, int index) {
        if (index >= arr.length) return null; // Base case: not found
        if (arr[index].code.equals(target)) return arr[index]; // Base case: found
        return recursiveSearch(arr, target, index + 1); // Recursive call
    }

    private static void compute() {
        int t = 0;
        for (CourseData cd : courseMap.values()) t += cd.units;
        System.out.println("Total Units: " + t);
    }

    private static void saveCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (CourseData cd : courseMap.values()) {
                pw.println(cd.code + "," + cd.title + "," + cd.units);
            }
            System.out.println("Saved to CSV.");
        } catch (IOException e) {
            System.out.println("File write error.");
        }
    }

    private static void loadCSV() {
        courseMap.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    courseMap.put(parts[0], new CourseData(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
            System.out.println("Loaded from CSV.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("File read error.");
        }
    }
}
