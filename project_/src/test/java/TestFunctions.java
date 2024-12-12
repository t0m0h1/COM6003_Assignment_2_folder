import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit.jupiter.PowerMockExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PowerMockExtension.class)
public class TestFunctions {

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testInitializeModules() {
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();

        // Ensure the maps have been initialized correctly
        assertEquals(3, app.level4Modules.size());
        assertEquals(3, app.level5Modules.size());
        assertEquals(3, app.level6Modules.size());

        // Check module names for a specific course and level
        String[] level4CSModules = app.level4Modules.get("BSc Computer Science");
        assertArrayEquals(new String[]{
                "Programming Fundamentals", 
                "Data Structures and Algorithms", 
                "Computer Networks"
        }, level4CSModules);
    }

    @Test
    public void testAddStudent() {
        // Mock JOptionPane to simulate user input for adding a student
        PowerMockito.mockStatic(JOptionPane.class);
        when(JOptionPane.showInputDialog(anyString()))
            .thenReturn("STID1234", "John", "Doe", "123 Main St", "01/01/2000", "07987654321", "johndoe@example.com", "Male", "BSc Computer Science", "Level 4");

        // Create the app instance and simulate button click
        CompSciApplicationVersion1 app = new CompSciApplicationVersion1();
        app.btnAddActionPerformed(null);

        // Verify student details in studentList
        assertEquals(1, app.studentList.size());
        Student student = app.studentList.get(0);
        assertEquals("STID1234", student.studentID);

        // Verify JTable model updates correctly
        TableModel tableModel = app.jTable1.getModel();
        assertEquals(1, tableModel.getRowCount());
    }
}
