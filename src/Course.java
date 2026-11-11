import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;

class CourseObj {
    String code, title;
    int units;
    CourseObj(String c, String t, int u) {
        code = c.toUpperCase().trim();
        title = t.trim();
        units = u;
    }
}

public class Course {
    private static ArrayList<CourseObj> list = new ArrayList<>();
    private static final String FILE = "courses_logic3.bin";

    public static void main(String[] args) {
        // Start the recursive GUI menu
        recursiveMenu();
    }

    // RECURSION APPLIED HERE
    private static void recursiveMenu() {
        String menu = "Student Course Management GUI\n" +
                "1. Add Course\n2. View Courses\n3. Search Course\n" +
                "4. Compute Units\n5. Save File\n6. Load File\n7. Exit\n" +
                "Enter option (1-7):";
        
        String input = JOptionPane.showInputDialog(null, menu, "Main Menu", JOptionPane.QUESTION_MESSAGE);
        
        // Handle cancel/close
        if (input == null || input.trim().equals("7")) {
            JOptionPane.showMessageDialog(null, "Exiting System...");
            return; // Base case: exit recursion
        }

        try {
            int choice = Integer.parseInt(input.trim());
            processChoice(choice);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Recursive call to show menu again
        recursiveMenu();
    }

    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                try {
                    String c = JOptionPane.showInputDialog("Course Code:");
                    if(c == null) return;
                    String t = JOptionPane.showInputDialog("Course Title:");
                    String uStr = JOptionPane.showInputDialog("Units:");
                    int u = Integer.parseInt(uStr.trim());
                    list.add(new CourseObj(c, t, u));
                    JOptionPane.showMessageDialog(null, "Success!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid Unit Input!");
                }
                break;
            case 2:
                StringBuilder sb = new StringBuilder("--- Courses ---\n");
                for (CourseObj o : list) sb.append(o.code).append(" | ").append(o.title).append(" | ").append(o.units).append("\n");
                JOptionPane.showMessageDialog(null, list.isEmpty() ? "No Courses" : sb.toString());
                break;
            case 3:
                String target = JOptionPane.showInputDialog("Search Code:");
                if(target == null) return;
                target = target.trim().toUpperCase();
                boolean found = false;
                for (CourseObj o : list) {
                    if (o.code.equals(target)) {
                        JOptionPane.showMessageDialog(null, "Found: " + o.title + " (" + o.units + ")");
                        found = true; break;
                    }
                }
                if (!found) JOptionPane.showMessageDialog(null, "Not Found.");
                break;
            case 4:
                int sum = 0;
                for (CourseObj o : list) sum += o.units;
                JOptionPane.showMessageDialog(null, "Total Units: " + sum);
                break;
            case 5:
                try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE))) {
                    dos.writeInt(list.size());
                    for (CourseObj o : list) {
                        dos.writeUTF(o.code);
                        dos.writeUTF(o.title);
                        dos.writeInt(o.units);
                    }
                    JOptionPane.showMessageDialog(null, "Saved!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Save Error");
                }
                break;
            case 6:
                try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE))) {
                    list.clear();
                    int count = dis.readInt();
                    for (int i = 0; i < count; i++) {
                        list.add(new CourseObj(dis.readUTF(), dis.readUTF(), dis.readInt()));
                    }
                    JOptionPane.showMessageDialog(null, "Loaded!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Load Error");
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid Option (1-7).");
        }
    }
}
