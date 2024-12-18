package com.example.review;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;



import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestFunctions {


    // Existing tests
    @Test
    public void testInsertStringValidInput() throws BadLocationException {
        // Create an instance of a PlainDocument and an AttributeSet
        PlainDocument doc = new PlainDocument();
        AttributeSet attr = new SimpleAttributeSet();

        // Valid string input
        String input = "HelloWorld";
        int offset = 0;

        // Call insertString to insert the valid input
        doc.insertString(offset, input, attr);

        // Get the content after insertion
        String actual = doc.getText(0, doc.getLength());

        // Expected result (input should be inserted as is)
        String expected = "HelloWorld";

        // Verify that the text after insertion is correct
        assertEquals(expected, actual, "Text after insertion should match the valid input");
    }

    @Test
    public void testInsertStringValidWithSpaceAndHyphen() throws BadLocationException {
        // Create an instance of a PlainDocument and an AttributeSet
        PlainDocument doc = new PlainDocument();
        AttributeSet attr = new SimpleAttributeSet();

        // Valid string input with space and hyphen
        String input = "Hello- World";
        int offset = 0;

        // Call insertString to insert the valid input
        doc.insertString(offset, input, attr);

        // Get the content after insertion
        String actual = doc.getText(0, doc.getLength());

        // Expected result (input should be inserted as is)
        String expected = "Hello- World";

        // Verify that the text after insertion is correct
        assertEquals(expected, actual, "Text after insertion should match the valid input with space and hyphen");
    }

    @Test
    public void testGenerateMarksArrayCorrectValues() {
        // Create an instance of the class containing generateMarksArray
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();

        // Call the method to get the actual result
        Integer[] actualMarks = app.generateMarksArray();

        // Expected values for the marks array
        Integer[] expectedMarks = new Integer[]{
            2, 5, 8,    // First band
            12, 15, 18, // Second band
            22, 25, 28, // Third band
            32, 35, 38, // Fourth band
            42, 45, 48, // Fifth band
            52, 55, 58, // Sixth band
            62, 65, 68, // Seventh band
            72, 75, 78, // Eighth band
            82, 85, 88, // Ninth band
            92, 95, 98  // Tenth band
        };

        // Verify that the actual array is equal to the expected array
        assertArrayEquals(expectedMarks, actualMarks, "The generated marks array doesn't match the expected values.");
    }

    @Test
    public void testGenerateMarksArraySize() {
        // Create an instance of the class containing generateMarksArray
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();

        // Call the method to get the actual result
        Integer[] actualMarks = app.generateMarksArray();

        // The expected size should be 30
        int expectedSize = 30;

        // Verify that the generated array has the correct size
        assertEquals(expectedSize, actualMarks.length, "The size of the generated marks array is incorrect.");
    }

    // Single test for displayModules
    @Test
    public void testDisplayModulesValidSelection() {
        // Initialize combo boxes
        JComboBox<String> cboCourse = new JComboBox<>(new String[]{"Course A", "Course B"});
        JComboBox<String> cboLevel = new JComboBox<>(new String[]{"Level 4", "Level 5", "Level 6"});

        // Initialize text area for module display
        JTextArea moduleDisplay = new JTextArea();

        // Initialize module mappings
        Map<String, String[]> level4Modules = new HashMap<>();
        level4Modules.put("Course A", new String[]{"Module 1", "Module 2", "Module 3"});

        Map<String, String[]> level5Modules = new HashMap<>();
        level5Modules.put("Course A", new String[]{"Module 4", "Module 5"});

        Map<String, String[]> level6Modules = new HashMap<>();
        level6Modules.put("Course B", new String[]{"Module 6", "Module 7", "Module 8"});

        // Simulate selections
        cboCourse.setSelectedItem("Course A");
        cboLevel.setSelectedItem("Level 4");

        // Call the displayModules method
        String selectedCourse = (String) cboCourse.getSelectedItem();
        String selectedLevel = (String) cboLevel.getSelectedItem();
        String[] modules = null;

        if ("Level 4".equals(selectedLevel)) {
            modules = level4Modules.get(selectedCourse);
        } else if ("Level 5".equals(selectedLevel)) {
            modules = level5Modules.get(selectedCourse);
        } else if ("Level 6".equals(selectedLevel)) {
            modules = level6Modules.get(selectedCourse);
        }

        if (modules != null) {
            moduleDisplay.setText(String.join("\n", modules));
        } else {
            moduleDisplay.setText("No modules available for this selection.");
        }

        // Expected output
        String expected = "Module 1\nModule 2\nModule 3";

        // Verify the module display contains the expected text
        assertEquals(expected, moduleDisplay.getText(), "Modules for Course A, Level 4 should match the expected list.");
    }


// Test Cases for displayModules
@Test
public void testDisplayModules_emptyModulesArray() {
    // Declare level4Modules map
    Map<String, String[]> level4Modules = new HashMap<>();
    level4Modules.put("Course A", new String[]{"Module 1", "Module 2", "Module 3"});
    level4Modules.put("Course D", new String[]{});

    // Declare combo boxes for course and level
    JComboBox<String> cboCourse = new JComboBox<>(new String[]{"Course A", "Course D"});
    JComboBox<String> cboLevel = new JComboBox<>(new String[]{"Level 4", "Level 5", "Level 6"});

    // JTextArea to display modules
    JTextArea moduleDisplay = new JTextArea();

    // Simulate selection in combo boxes
    cboCourse.setSelectedItem("Course D");
    cboLevel.setSelectedItem("Level 4");

    // Get selected course and level
    String selectedCourse = (String) cboCourse.getSelectedItem();
    String selectedLevel = (String) cboLevel.getSelectedItem();
    String[] modules = null;

    // Retrieve the modules based on the selected course and level
    if ("Level 4".equals(selectedLevel)) {
        modules = level4Modules.get(selectedCourse);
    }

    // Set the text for moduleDisplay based on modules
    if (modules != null && modules.length > 0) {
        moduleDisplay.setText(String.join("\n", modules));
    } else {
        moduleDisplay.setText("No modules available for this selection.");
    }

    // Assert that the fallback message is displayed when the module array is empty
    String expected = "No modules available for this selection.";
    assertEquals(expected, moduleDisplay.getText(), "Fallback message should be displayed for an empty modules array.");
}



// Test for valid data in validate input method

@Test
public void testValidateInput_validData() throws Exception {
    // Create instance of the class
    CompSciApplicationVersion1 app = new CompSciApplicationVersion1();

    // Use reflection to access the private method
    Method method = CompSciApplicationVersion1.class.getDeclaredMethod("validateInput");
    method.setAccessible(true);  // Make the private method accessible

    // Call the method
    boolean isValid = (boolean) method.invoke(app);

    // Assert the result
    assertTrue(isValid, "Valid input should return true.");
}










@Test
public void testValidateInput_invalidStudentID() {
    // Simulate filling out the fields with invalid student ID
    jtxtStudentID.setText("12345");  // Invalid format (should be "STID####")
    jtxtEmail.setText("student@example.com");
    jtxtFirstname.setText("John");
    jtxtLastname.setText("Doe");
    jtxtAddress.setText("123 Main Street, City");
    jtxtDOB.setText("01/01/2000");
    jtxtMobile.setText("07123456789");

    // Create an instance of CompSciApplicationVersion1 and call the validateInput method
    CompSciApplicationVersion1 app = new CompSciApplicationVersion1();

    // Call the validateInput method to check the validation
    boolean isValid = app.validateInput();

    // Assert that the validation returns false for an invalid Student ID
    assertFalse(isValid, "Invalid student ID should return false.");
}



}