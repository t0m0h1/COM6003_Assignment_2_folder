package com.example.review;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;

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
}
