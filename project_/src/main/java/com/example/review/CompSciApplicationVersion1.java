package com.example.review;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author J.Diockou
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

// Custom document for restricting input (only letters and hyphens)
class JTextFieldLimit extends javax.swing.text.PlainDocument {
    private final int limit;

    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
        if (str == null) return;
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str.replaceAll("[^a-zA-Z-]", ""), attr);
        }
    }
}

public class CompSciApplicationVersion1 extends JFrame {

    private static final String DATABASE_URL = "jdbc:sqlite:compsci.db";
    private JTextField jtxtStudentID, jtxtFirstname, jtxtLastname, jtxtAddress, jtxtDOB, jtxtMobile, jtxtEmail;
    private JComboBox<String> cboGender, cboCourse, cboLevel;
    private JTextArea moduleDisplay, resultsDisplay;
    private JTable jTable1;
    private DefaultTableModel tableModel;
    private JComboBox<Integer>[] moduleMarksDropdowns;

    private final Map<String, String[]> level4Modules;
    private final Map<String, String[]> level5Modules;
    private final Map<String, String[]> level6Modules;

    private final ArrayList<Student> studentList;

    public CompSciApplicationVersion1() {
        level4Modules = new HashMap<>();
        level5Modules = new HashMap<>();
        level6Modules = new HashMap<>();
        initializeModules();

        studentList = new ArrayList<>();

        setTitle("CompSci Management Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        initComponents();
        initializeDatabase();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel, BorderLayout.CENTER);

        JLabel headerLabel = new JLabel("CompSci Management Application", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Personal Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize text fields
        jtxtStudentID = new JTextField(15);
        jtxtFirstname = new JTextField(15);
        jtxtLastname = new JTextField(15);
        jtxtAddress = new JTextField(15);
        jtxtDOB = new JTextField(15);
        jtxtMobile = new JTextField(15);
        jtxtEmail = new JTextField(15);
        jtxtStudentID.setToolTipText("Enter Student ID in the format STID#### (e.g., STID1234)");
        jtxtFirstname.setToolTipText("Enter your first name (letters and hyphen only, capitalized first letter)");
        jtxtLastname.setToolTipText("Enter your last name (letters and hyphen only, capitalized first letter)");
        jtxtAddress.setToolTipText("Enter your address (door number, street name, postcode, county, country)");
        jtxtDOB.setToolTipText("Enter your date of birth in DD/MM/YYYY format");
        jtxtMobile.setToolTipText("Enter your mobile number (e.g., 07xxx xxxxxx or +44 7xxx xxxxxx)");
        jtxtEmail.setToolTipText("Enter a valid email address (e.g., example@example.com)");

        // ComboBoxes
        cboGender = new JComboBox<>(new String[]{"", "Male", "Female", "Other"});
        cboCourse = new JComboBox<>(new String[]{"", "BSc Computer Science", "BSc Data Science", "BA Games"});
        cboLevel = new JComboBox<>(new String[]{"", "Level 4", "Level 5", "Level 6"});
        
        // Marks generation
        Integer[] marks = generateMarksArray();
        moduleMarksDropdowns = new JComboBox[4];
        
        // Initialize marks dropdowns
        for (int i = 0; i < moduleMarksDropdowns.length; i++) {
            moduleMarksDropdowns[i] = new JComboBox<>(new Integer[marks.length + 1]);
            moduleMarksDropdowns[i].setModel(new DefaultComboBoxModel<>(marks));
            moduleMarksDropdowns[i].insertItemAt(null, 0); // Insert blank option as the first item
            moduleMarksDropdowns[i].setSelectedIndex(0); // Set the default selection to blank (null)
            moduleMarksDropdowns[i].setForeground(Color.GREEN);
        }

        // Text areas for displaying modules and results
        moduleDisplay = new JTextArea(5, 15);
        resultsDisplay = new JTextArea(3, 20);
        moduleDisplay.setEditable(false);
        resultsDisplay.setEditable(false);
        moduleDisplay.setBorder(BorderFactory.createTitledBorder("Modules"));
        resultsDisplay.setBorder(BorderFactory.createTitledBorder("Results"));
        moduleDisplay.setFont(new Font("Garamond", Font.PLAIN, 14));
        moduleDisplay.setForeground(new Color(0, 51, 102)); // Garamond Blue

        // Add action listeners for combo boxes
        cboCourse.addActionListener(e -> displayModules());
        cboLevel.addActionListener(e -> displayModules());

        // Add form components
        addFormRow(formPanel, gbc, 0, "Student ID:", jtxtStudentID);
        addFormRow(formPanel, gbc, 1, "First Name:", jtxtFirstname);
        addFormRow(formPanel, gbc, 2, "Last Name:", jtxtLastname);
        addFormRow(formPanel, gbc, 3, "Address:", jtxtAddress);
        addFormRow(formPanel, gbc, 4, "DOB (DD/MM/YYYY):", jtxtDOB);
        addFormRow(formPanel, gbc, 5, "Mobile:", jtxtMobile);
        addFormRow(formPanel, gbc, 6, "Email:", jtxtEmail);
        addFormRow(formPanel, gbc, 7, "Gender:", cboGender);
        addFormRow(formPanel, gbc, 8, "Course:", cboCourse);
        addFormRow(formPanel, gbc, 9, "Level:", cboLevel);
        addFormRow(formPanel, gbc, 10, "Modules:", moduleDisplay);

        // Adding marks dropdowns
        for (int i = 0; i < moduleMarksDropdowns.length; i++) {
            addFormRow(formPanel, gbc, 11 + i, "Module " + (i + 1) + " Marks:", moduleMarksDropdowns[i]);
        }

        mainPanel.add(formPanel, BorderLayout.WEST);

        // Table to display student records
        String[] columnNames = {"ID", "StudentID", "First Name", "Last Name", "Address", "Gender", "DOB", "Mobile", "Email", "Course"};
        tableModel = new DefaultTableModel(columnNames, 0);
        jTable1 = new JTable(tableModel);
        jTable1.setRowHeight(25);
        jTable1.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane tableScrollPane = new JScrollPane(jTable1);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Student Records"));
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");
        JButton btnBackup = new JButton("Backup to Database");
        JButton btnReset = new JButton("Reset");
        JButton btnExit = new JButton("Exit");
        JButton btnCalculate = new JButton("Calculate Results");

        // Button panel setup
        btnAdd.setPreferredSize(new Dimension(120, 30));
        btnRemove.setPreferredSize(new Dimension(120, 30));
        btnBackup.setPreferredSize(new Dimension(160, 30));
        btnReset.setPreferredSize(new Dimension(120, 30));
        btnExit.setPreferredSize(new Dimension(120, 30));
        btnCalculate.setPreferredSize(new Dimension(120, 30));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnBackup);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnExit);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Button listeners
        btnAdd.addActionListener(this::btnAddActionPerformed);
        btnRemove.addActionListener(this::btnRemoveActionPerformed);
        btnBackup.addActionListener(this::btnBackupActionPerformed);
        btnReset.addActionListener(this::btnResetActionPerformed);
        btnExit.addActionListener(this::btnExitActionPerformed);
        btnCalculate.addActionListener(this::btnCalculateActionPerformed);  // Added here

        mainPanel.add(resultsDisplay, BorderLayout.EAST);
    }

    private class Student {
        String studentID, firstName, lastName, address, dob, mobile, email, gender, course, level;
        int[] moduleMarks;

        public Student(String studentID, String firstName, String lastName, String address, String dob, String mobile,
                       String email, String gender, String course, String level, int[] moduleMarks) {
            this.studentID = studentID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.dob = dob;
            this.mobile = mobile;
            this.email = email;
            this.gender = gender;
            this.course = course;
            this.level = level;
            this.moduleMarks = moduleMarks;
        }
    }

    protected Integer[] generateMarksArray() {
        Integer[] marks = new Integer[]{
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
        return marks;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, Component component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "studentID TEXT, firstName TEXT, lastName TEXT, address TEXT, dob TEXT, mobile TEXT, email TEXT," +
                    "gender TEXT, course TEXT, level TEXT, marks TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayModules() {
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
    }

    private void btnCalculateActionPerformed(ActionEvent evt) {
        String selectedLevel = (String) cboLevel.getSelectedItem();
        int totalMarks = 0;

        // Calculate the total marks for selected modules
        for (JComboBox<Integer> marksDropdown : moduleMarksDropdowns) {
            if (marksDropdown.getSelectedItem() != null) {
                totalMarks += (int) marksDropdown.getSelectedItem();
            }
        }

        int averageMarks = totalMarks / moduleMarksDropdowns.length;

        // If it's Level 4 or Level 5, display total average marks
        if ("Level 4".equals(selectedLevel) || "Level 5".equals(selectedLevel)) {
            resultsDisplay.setText("Total Average Marks for " + selectedLevel + ": " + averageMarks);
            return;
        }

        // For Level 6, check if any module has failed
        boolean hasFailedModule = false;
        for (JComboBox<Integer> marksDropdown : moduleMarksDropdowns) {
            if (marksDropdown.getSelectedItem() != null && (int) marksDropdown.getSelectedItem() < 40) {
                hasFailedModule = true;
                break;
            }
        }

        // If any module has failed, determine Higher National Diploma (HND) classification
        if (hasFailedModule) {
            String hndClassification;
            if (averageMarks >= 70) {
                hndClassification = "Higher National Diploma with Distinction";
            } else if (averageMarks >= 60) {
                hndClassification = "Higher National Diploma with Merit";
            } else if (averageMarks >= 40) {
                hndClassification = "Higher National Diploma with Pass";
            } else {
                hndClassification = "Higher National Diploma (Fail)";
            }
            resultsDisplay.setText("Qualification: " + hndClassification);
        } else {
            // If no module failed, determine Degree classification
            String degreeClassification;
            if (averageMarks >= 70) {
                degreeClassification = "First Class";
            } else if (averageMarks >= 65) {
                degreeClassification = "Upper Second Class";
            } else if (averageMarks >= 60) {
                degreeClassification = "Lower Second Class";
            } else {
                degreeClassification = "Third Class";
            }
            resultsDisplay.setText("Total Average Marks: " + averageMarks + "\nClassification: " + degreeClassification);
        }
    }

    private void btnAddActionPerformed(ActionEvent evt) {
        // Validate input fields
        if (!validateInput()) {
            return;  // If validation fails, return without proceeding
        }

        // Check for duplicate Student ID
        String studentID = jtxtStudentID.getText();
        if (isStudentIDExist(studentID)) {
            JOptionPane.showMessageDialog(this, "Student ID already exists. Please use a different Student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Retrieve data from the form fields
        String firstName = jtxtFirstname.getText();
        String lastName = jtxtLastname.getText();
        String address = jtxtAddress.getText();
        String dob = jtxtDOB.getText();
        String mobile = jtxtMobile.getText();
        String email = jtxtEmail.getText();
        String gender = (String) cboGender.getSelectedItem();
        String course = (String) cboCourse.getSelectedItem();
        String level = (String) cboLevel.getSelectedItem();

        // Initialize the moduleMarks array to store the marks
        int[] moduleMarks = new int[moduleMarksDropdowns.length];
        for (int i = 0; i < moduleMarksDropdowns.length; i++) {
            if (moduleMarksDropdowns[i].getSelectedItem() != null) {
                moduleMarks[i] = (int) moduleMarksDropdowns[i].getSelectedItem();
            }
        }

        // Create a new Student object and add to the list
        Student newStudent = new Student(studentID, firstName, lastName, address, dob, mobile, email, gender, course, level, moduleMarks);
        studentList.add(newStudent);

        // Add the new student to the JTable (model) for display
        tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, studentID, firstName, lastName, address, gender, dob, mobile, email, course});

        // Show a confirmation message to the user
        JOptionPane.showMessageDialog(this, "Student data saved in memory. Save to database when ready.");
    }

    private boolean isStudentIDExist(String studentID) {
        for (Student student : studentList) {
            if (student.studentID.equals(studentID)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateInput() {
        // Validate Student ID
        String studentID = jtxtStudentID.getText();
        if (!studentID.matches("^STID\\d{4}$")) {
            JOptionPane.showMessageDialog(this, "Student ID must be in the format STID#### (e.g., STID1234).", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate Email
        String email = jtxtEmail.getText();
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher emailMatcher = emailPattern.matcher(email);
        if (!emailMatcher.matches()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate First and Last Name (letters only, capitalized first letter)
        String firstName = jtxtFirstname.getText();
        String lastName = jtxtLastname.getText();
        if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Names can only contain letters and must start with a capital letter.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Capitalize the first letter of first and last names
        jtxtFirstname.setText(capitalizeName(firstName));
        jtxtLastname.setText(capitalizeName(lastName));

        // Validate Address
        String address = jtxtAddress.getText();
        if (address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate Date of Birth (DD/MM/YYYY format)
        String dob = jtxtDOB.getText();
        if (!dob.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
            JOptionPane.showMessageDialog(this, "Date of birth must be in DD/MM/YYYY format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate Mobile Number
        String mobile = jtxtMobile.getText();
        if (!mobile.matches("^(07\\d{3}\\s?\\d{3}\\s?\\d{3}|\\+44\\s?7\\d{3}\\s?\\d{3}\\s?\\d{3})$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid UK mobile number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;  // If all validations pass
    }

    private String capitalizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private void btnRemoveActionPerformed(ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            String studentID = (String) tableModel.getValueAt(selectedRow, 1);

            studentList.removeIf(student -> student.studentID.equals(studentID));

            try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
                String sql = "DELETE FROM students WHERE studentID = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, studentID);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Student removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "No student selected.");
        }
    }

    private void btnBackupActionPerformed(ActionEvent evt) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            String sql = "INSERT INTO students (studentID, firstName, lastName, address, dob, mobile, email, gender, course, level, marks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Student student : studentList) {
                pstmt.setString(1, student.studentID);
                pstmt.setString(2, student.firstName);
                pstmt.setString(3, student.lastName);
                pstmt.setString(4, student.address);
                pstmt.setString(5, student.dob);
                pstmt.setString(6, student.mobile);
                pstmt.setString(7, student.email);
                pstmt.setString(8, student.gender);
                pstmt.setString(9, student.course);
                pstmt.setString(10, student.level);
                pstmt.setString(11, marksToString(student.moduleMarks));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            JOptionPane.showMessageDialog(this, "Data backed up to the database.");
            studentList.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String marksToString(int[] marks) {
        StringBuilder sb = new StringBuilder();
        for (int mark : marks) {
            sb.append(mark).append(",");
        }
        return sb.toString();
    }

    private void initializeModules() {
        level4Modules.put("BSc Computer Science", new String[]{
                "COM4103 Software Development", "COM4043 Computing Skills", "COM4113 Tech Stack", "COM4143 User Experience"});
        level5Modules.put("BSc Computer Science", new String[]{
                "COM5103 Software Engineering", "COM5303 Computer Networking and Security", "COM5113 Algorithms and Data Structures", "COM5203 Machine Learning"});
        level6Modules.put("BSc Computer Science", new String[]{
                "COM6305 Secure Development and Deployment", "COM6203 Applied Artificial Intelligence", "COM6103 Data Science", "COM6043 Project"});

        level4Modules.put("BSc Data Science", new String[]{
                "COM4103 Software Development", "COM4043 Computing Skills", "COM4113 Tech Stack", "COM4143 User Experience"});
        level5Modules.put("BSc Data Science", new String[]{
                "COM5103 Software Engineering", "COM5303 Computer Networking and Security", "COM5113 Algorithms and Data Structures", "COM5203 Machine Learning"});
        level6Modules.put("BSc Data Science", new String[]{
                "COM6103 Data Science", "COM6303 Cyber Security", "COM6105 Mobile Computing", "COM6043 Project"});

        level4Modules.put("BA Games", new String[]{
                "COM4103 Software Development", "COM4043 Computing Skills", "COM4113 Tech Stack", "COM4143 User Experience"});
        level5Modules.put("BA Games", new String[]{
                "COM5203 Machine Learning", "COM5403 Game Technologies", "COM5313 Ethical Hacking", "COM5043 Thematic Project"});
        level6Modules.put("BA Games", new String[]{
                "COM6303 Cyber Security", "COM6403 Games Development", "COM6105 Mobile Computing", "COM6043 Project"});
    }

    private void btnResetActionPerformed(ActionEvent evt) {
        jtxtStudentID.setText("");
        jtxtFirstname.setText("");
        jtxtLastname.setText("");
        jtxtAddress.setText("");
        jtxtDOB.setText("");
        jtxtMobile.setText("");
        jtxtEmail.setText("");
        cboGender.setSelectedIndex(0);
        cboCourse.setSelectedIndex(0);
        cboLevel.setSelectedIndex(0);
        for (JComboBox<Integer> marksDropdown : moduleMarksDropdowns) {
            marksDropdown.setSelectedIndex(0);
        }
        resultsDisplay.setText("");
    }

    private void btnExitActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CompSciApplicationVersion1::new);
    }
}