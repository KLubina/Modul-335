package com.example.m335;

import com.example.m335.model.Module;
import com.example.m335.viewmodel.ModuleViewModel;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Module management functionality.
 */
public class ModuleUnitTest {

    /**
     * Test case 1: Creation of a valid module with complete data
     * Verifies that a module can be created with valid data and the average is calculated correctly.
     */
    @Test
    public void testValidModuleCreation() {
        // Create a valid module with complete information
        Module module = new Module("M123", "Testmodul Vollständig");
        module.setZpNote(5.0f);
        module.setLbNote(5.5f);

        // Verify module properties
        assertEquals("M123", module.getModuleNumber());
        assertEquals("Testmodul Vollständig", module.getModuleTitle());
        assertEquals(Float.valueOf(5.0f), module.getZpNote());
        assertEquals(Float.valueOf(5.5f), module.getLbNote());

        // Verify average calculation - expected: (5.0 + 5.5) / 2 = 5.25
        assertEquals(Float.valueOf(5.25f), module.getAverageGrade());
        assertTrue(module.hasAllGrades());
    }

    /**
     * Test case 2: Validation of module number length
     * Tests the validation logic for module number minimum length requirement.
     */
    @Test
    public void testModuleNumberValidation() {
        // Test values
        String tooShortNumber = "M12";  // Less than 4 characters
        String validNumber = "M123";    // Exactly 4 characters
        String validTitle = "Test Module";

        // Simple validation check
        assertFalse("Module number with less than 4 characters should be invalid",
                isValidModuleInput(tooShortNumber, validTitle));
        assertTrue("Module number with at least 4 characters should be valid",
                isValidModuleInput(validNumber, validTitle));
    }

    /**
     * Test case 3: Updating an existing module with grades
     * Verifies that grades can be added to an existing module and the average updates correctly.
     */
    @Test
    public void testModuleGradeUpdate() {
        // Create a module without grades initially
        Module module = new Module("M456", "Bestehendes Modul");

        // Verify no average grade initially
        assertNull("New module should have no average grade", module.getAverageGrade());
        assertFalse("New module should not have all grades", module.hasAllGrades());

        // Update grades
        module.setZpNote(4.5f);
        module.setLbNote(5.0f);

        // Verify updated grades and average
        assertEquals(Float.valueOf(4.5f), module.getZpNote());
        assertEquals(Float.valueOf(5.0f), module.getLbNote());
        assertEquals(Float.valueOf(4.75f), module.getAverageGrade());
        assertTrue("Module should now have all grades", module.hasAllGrades());
    }

    /**
     * Test case 4: Validation of grade values
     * Tests the validation logic for grade values to be within valid range.
     */
    @Test
    public void testGradeValidation() {
        // Valid input for required fields
        String validNumber = "M789";
        String validTitle = "Testmodul Noten";

        // Valid grade strings
        String validGrade = "4.5";
        String tooHighGrade = "7.0";   // Above valid range (1-6)
        String tooLowGrade = "0.5";    // Below valid range (1-6)
        String invalidGradeFormat = "abc"; // Not a number

        // Test valid grade
        assertTrue(isValidGrade(validGrade));

        // Test invalid grades
        assertFalse("Grade above 6.0 should be invalid", isValidGrade(tooHighGrade));
        assertFalse("Grade below 1.0 should be invalid", isValidGrade(tooLowGrade));
        assertFalse("Non-numeric grade should be invalid", isValidGrade(invalidGradeFormat));
    }

    /**
     * Helper method that simulates module input validation
     */
    private boolean isValidModuleInput(String moduleNumber, String moduleTitle) {
        return moduleNumber != null && moduleNumber.length() >= 4 &&
                moduleTitle != null && moduleTitle.length() >= 4;
    }

    /**
     * Helper method that simulates grade validation
     */
    private boolean isValidGrade(String gradeStr) {
        try {
            float grade = Float.parseFloat(gradeStr);
            return grade >= 1.0f && grade <= 6.0f;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}