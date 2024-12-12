package com.example.review;  // Move this to the top



import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestFunctions {

    // Test for valid input
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

    // Test for valid input: inserting a string with spaces and a hyphen (allowed characters)
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




    // Testing generate marks array function

    // correct expected values
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


    //  correct size
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

}
