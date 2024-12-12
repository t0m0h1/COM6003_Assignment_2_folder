package com.example.review;  // Move this to the top

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
// import com.example.review.CompSciApplicationVersion1;  // Import if needed?

public class TestFunctions {

    @Test
    public void testGenerateMarksArray() {
        // Create an instance of the CompSciApplicationVersion1 class
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();

        // Call the method to get the actual result
        Integer[] actualMarks = app.generateMarksArray();

        // Expected values
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

        // Verify that the arrays are identical in size and content
        assertArrayEquals(expectedMarks, actualMarks, "Array values mismatch");
    }
}
