import java.io.Serializable;

/**
 * Course Class
 * Encapsulates data for a single course including course code, course title, and credit units.
 *
 * Author: Moses Abutu Audu
 * Matric No: 2025/A/CSC/0179
 * Course: COS 201 - Programming I
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String courseTitle;
    private int creditUnits;

    public Course(String courseCode, String courseTitle, int creditUnits) {
        setCourseCode(courseCode);
        setCourseTitle(courseTitle);
        setCreditUnits(creditUnits);
    }

    // Getters and Setters with basic validation and formatting
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty.");
        }
        this.courseCode = courseCode.trim().toUpperCase();
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        if (courseTitle == null || courseTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be empty.");
        }
        this.courseTitle = courseTitle.trim();
    }

    public int getCreditUnits() {
        return creditUnits;
    }

    public void setCreditUnits(int creditUnits) {
        if (creditUnits <= 0) {
            throw new IllegalArgumentException("Credit units must be a positive integer.");
        }
        this.creditUnits = creditUnits;
    }

    /**
     * Converts course object into a delimited string format for file storage.
     */
    public String toFileFormat() {
        return courseCode + "|" + courseTitle + "|" + creditUnits;
    }

    /**
     * Reconstructs a Course object from a delimited file string line.
     */
    public static Course fromFileFormat(String fileLine) {
        String[] parts = fileLine.split("\\|");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid data format in file line: " + fileLine);
        }
        String code = parts[0].trim();
        String title = parts[1].trim();
        int units = Integer.parseInt(parts[2].trim());
        return new Course(code, title, units);
    }

    @Override
    public String toString() {
        return String.format("%-12s | %-35s | %2d Units", courseCode, courseTitle, creditUnits);
    }
}