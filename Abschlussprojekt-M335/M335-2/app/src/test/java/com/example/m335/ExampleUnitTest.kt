package com.example.m335

import com.example.m335.model.Module
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 */
class ExampleUnitTest {
    /**
     * Original example test
     */
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    /**
     * Test case 1: Creation of a valid module with complete data
     */
    @Test
    fun testValidModuleCreation() {
        // Create a valid module with complete information
        val module = Module("M123", "Testmodul Vollständig")
        module.zpNote = 5.0f
        module.lbNote = 5.5f

        // Verify module properties
        assertEquals("M123", module.moduleNumber)
        assertEquals("Testmodul Vollständig", module.moduleTitle)
        assertEquals(5.0f, module.zpNote)
        assertEquals(5.5f, module.lbNote)

        // Verify average calculation
        assertEquals(5.25f, module.averageGrade)
        assertTrue(module.hasAllGrades())
    }

    /**
     * Test case 2: Validation of module number length
     */
    @Test
    fun testModuleNumberValidation() {
        // Test values
        val tooShortNumber = "M12"  // Less than 4 characters
        val validNumber = "M123"    // Exactly 4 characters
        val validTitle = "Test Module"

        // Simple validation check
        assertFalse("Module number with less than 4 characters should be invalid",
            isValidModuleInput(tooShortNumber, validTitle))
        assertTrue("Module number with at least 4 characters should be valid",
            isValidModuleInput(validNumber, validTitle))
    }

    /**
     * Test case 3: Updating an existing module with grades
     */
    @Test
    fun testModuleGradeUpdate() {
        // Create a module without grades initially
        val module = Module("M456", "Bestehendes Modul")

        // Verify no average grade initially
        assertNull("New module should have no average grade", module.averageGrade)
        assertFalse("New module should not have all grades", module.hasAllGrades())

        // Update grades
        module.zpNote = 4.5f
        module.lbNote = 5.0f

        // Verify updated grades and average
        assertEquals(4.5f, module.zpNote)
        assertEquals(5.0f, module.lbNote)
        assertEquals(4.75f, module.averageGrade)
        assertTrue("Module should now have all grades", module.hasAllGrades())
    }

    /**
     * Test case 4: Validation of grade values
     */
    @Test
    fun testGradeValidation() {
        // Valid grade strings
        val validGrade = "4.5"
        val tooHighGrade = "7.0"   // Above valid range (1-6)
        val tooLowGrade = "0.5"    // Below valid range (1-6)
        val invalidGradeFormat = "abc" // Not a number

        // Test valid grade
        assertTrue(isValidGrade(validGrade))

        // Test invalid grades
        assertFalse("Grade above 6.0 should be invalid", isValidGrade(tooHighGrade))
        assertFalse("Grade below 1.0 should be invalid", isValidGrade(tooLowGrade))
        assertFalse("Non-numeric grade should be invalid", isValidGrade(invalidGradeFormat))
    }

    /**
     * Helper method that simulates module input validation
     */
    private fun isValidModuleInput(moduleNumber: String?, moduleTitle: String?): Boolean {
        return moduleNumber != null && moduleNumber.length >= 4 &&
                moduleTitle != null && moduleTitle.length >= 4
    }

    /**
     * Helper method that simulates grade validation
     */
    private fun isValidGrade(gradeStr: String): Boolean {
        return try {
            val grade = gradeStr.toFloat()
            grade >= 1.0f && grade <= 6.0f
        } catch (e: NumberFormatException) {
            false
        }
    }
}