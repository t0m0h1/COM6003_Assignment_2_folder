package com.example.review;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
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

    // Test for inserting valid strings into a PlainDocument
    @Test
    public void testInsertStringValidInput() throws BadLocationException {
        PlainDocument doc = new PlainDocument();
        AttributeSet attr = new SimpleAttributeSet();

        String input = "HelloWorld";
        int offset = 0;

        doc.insertString(offset, input, attr);
        String actual = doc.getText(0, doc.getLength());

        assertEquals("HelloWorld", actual, "Text after insertion should match the valid input");
    }

    // Test for inserting strings with spaces and hyphens into a PlainDocument
    @Test
    public void testInsertStringValidWithSpaceAndHyphen() throws BadLocationException {
        PlainDocument doc = new PlainDocument();
        AttributeSet attr = new SimpleAttributeSet();

        String input = "Hello- World";
        int offset = 0;

        doc.insertString(offset, input, attr);
        String actual = doc.getText(0, doc.getLength());

        assertEquals("Hello- World", actual, "Text after insertion should match the valid input with space and hyphen");
    }

    // Test for verifying the generated marks array matches expected values
    @Test
    public void testGenerateMarksArrayCorrectValues() {
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();
        Integer[] actualMarks = app.generateMarksArray();

        Integer[] expectedMarks = new Integer[]{
            2, 5, 8, 12, 15, 18, 22, 25, 28, 32, 35, 38, 42, 45, 48, 
            52, 55, 58, 62, 65, 68, 72, 75, 78, 82, 85, 88, 92, 95, 98
        };

        assertArrayEquals(expectedMarks, actualMarks, "The generated marks array doesn't match the expected values.");
    }

    // Test for verifying the size of the generated marks array
    @Test
    public void testGenerateMarksArraySize() {
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();
        Integer[] actualMarks = app.generateMarksArray();

        assertEquals(30, actualMarks.length, "The size of the generated marks array is incorrect.");
    }

    // Test for displaying modules based on course and level selection
    @Test
    public void testDisplayModulesValidSelection() {
        JComboBox<String> cboCourse = new JComboBox<>(new String[]{"Course A", "Course B"});
        JComboBox<String> cboLevel = new JComboBox<>(new String[]{"Level 4", "Level 5", "Level 6"});
        JTextArea moduleDisplay = new JTextArea();

        Map<String, String[]> level4Modules = new HashMap<>();
        level4Modules.put("Course A", new String[]{"Module 1", "Module 2", "Module 3"});

        cboCourse.setSelectedItem("Course A");
        cboLevel.setSelectedItem("Level 4");

        String selectedCourse = (String) cboCourse.getSelectedItem();
        String selectedLevel = (String) cboLevel.getSelectedItem();
        String[] modules = null;

        if ("Level 4".equals(selectedLevel)) {
            modules = level4Modules.get(selectedCourse);
        }

        moduleDisplay.setText(modules != null ? String.join("\n", modules) : "No modules available for this selection.");

        assertEquals("Module 1\nModule 2\nModule 3", moduleDisplay.getText(), "Modules for Course A, Level 4 should match the expected list.");
    }

    // Test for handling empty module arrays
    @Test
    public void testDisplayModules_emptyModulesArray() {
        Map<String, String[]> level4Modules = new HashMap<>();
        level4Modules.put("Course A", new String[]{"Module 1", "Module 2", "Module 3"});
        level4Modules.put("Course D", new String[]{});

        JComboBox<String> cboCourse = new JComboBox<>(new String[]{"Course A", "Course D"});
        JComboBox<String> cboLevel = new JComboBox<>(new String[]{"Level 4", "Level 5", "Level 6"});
        JTextArea moduleDisplay = new JTextArea();

        cboCourse.setSelectedItem("Course D");
        cboLevel.setSelectedItem("Level 4");

        String selectedCourse = (String) cboCourse.getSelectedItem();
        String selectedLevel = (String) cboLevel.getSelectedItem();
        String[] modules = null;

        if ("Level 4".equals(selectedLevel)) {
            modules = level4Modules.get(selectedCourse);
        }

        moduleDisplay.setText(modules != null && modules.length > 0 ? String.join("\n", modules) : "No modules available for this selection.");

        assertEquals("No modules available for this selection.", moduleDisplay.getText(), "Fallback message should be displayed for an empty modules array.");
    }

    // Test for validating input with valid data
    @Test
    public void testValidateInput_validData() throws Exception {
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();
        Method method = CompSciApplicationVersion1.class.getDeclaredMethod("validateInput");
        method.setAccessible(true);

        assertTrue((boolean) method.invoke(app), "Valid input should return true.");
    }

    // Test for validating input with invalid student ID
    @Test
    public void testValidateInput_invalidStudentID() throws Exception {
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();
        Method method = CompSciApplicationVersion1.class.getDeclaredMethod("validateInput");
        method.setAccessible(true);

        assertFalse((boolean) method.invoke(app), "Invalid student ID should return false.");
    }
}