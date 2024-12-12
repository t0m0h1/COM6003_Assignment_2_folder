package Project.test;


import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import org.junit.Test;

// import CompSciApplicationVersion1.*;

public class TestFunctions {

    @Test
    public void testInitializeModules() {
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();
        assertEquals(3, app.level4Modules.size());
        assertEquals(3, app.level5Modules.size());
        assertEquals(3, app.level6Modules.size());
    
        // Check module names for a specific course and level
        String[] level4CSModules = app.level4Modules.get("BSc Computer Science");
        assertArrayEquals(new String[]{"Programming Fundamentals", "Data Structures and Algorithms", "Computer Networks"}, level4CSModules);
    }



    // Testing pressing add student button 
    @Test
public void testAddStudent() {
    // Mock JOptionPane to simulate user input
    when(JOptionPane.showInputDialog(anyString())).thenReturn("STID1234", "John", "Doe", "123 Main St", "01/01/2000", "07987654321", "johndoe@example.com", "Male", "BSc Computer Science", "Level 4");

    CompSciApplicationVersion1 app = new CompSciApplicationVersion1();
    app.btnAddActionPerformed(null);

    // Verify student details in studentList and tableModel
    assertEquals(1, app.studentList.size());
    Student student = app.studentList.get(0);
    assertEquals("STID1234", student.studentID);


    TableModel tableModel = app.jTable1.getModel();
    assertEquals(1, tableModel.getRowCount());

}

}