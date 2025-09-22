package com.example.m335.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a module with its properties.
 * Stores information about module number, title, and grades.
 */
@Entity(tableName = "modules")
public class Module {

    @PrimaryKey
    @NonNull
    private String moduleNumber; // e.g. M106, M223, M335

    @NonNull
    private String moduleTitle; // e.g. Mobile Apps erstellen

    private Float zpNote; // Zwischenprüfungsnote
    private Float lbNote; // Leistungsbewertung

    /**
     * Constructor to create a new Module with required fields
     *
     * @param moduleNumber The unique module number (e.g. M335)
     * @param moduleTitle The title of the module
     */
    public Module(@NonNull String moduleNumber, @NonNull String moduleTitle) {
        this.moduleNumber = moduleNumber;
        this.moduleTitle = moduleTitle;
    }

    /**
     * Gets the module number
     *
     * @return The module number
     */
    @NonNull
    public String getModuleNumber() {
        return moduleNumber;
    }

    /**
     * Sets the module number
     *
     * @param moduleNumber The new module number
     */
    public void setModuleNumber(@NonNull String moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    /**
     * Gets the module title
     *
     * @return The module title
     */
    @NonNull
    public String getModuleTitle() {
        return moduleTitle;
    }

    /**
     * Sets the module title
     *
     * @param moduleTitle The new module title
     */
    public void setModuleTitle(@NonNull String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    /**
     * Gets the ZP grade
     *
     * @return The ZP grade or null if not set
     */
    public Float getZpNote() {
        return zpNote;
    }

    /**
     * Sets the ZP grade
     *
     * @param zpNote The new ZP grade
     */
    public void setZpNote(Float zpNote) {
        this.zpNote = zpNote;
    }

    /**
     * Gets the LB grade
     *
     * @return The LB grade or null if not set
     */
    public Float getLbNote() {
        return lbNote;
    }

    /**
     * Sets the LB grade
     *
     * @param lbNote The new LB grade
     */
    public void setLbNote(Float lbNote) {
        this.lbNote = lbNote;
    }

    /**
     * Calculates the average grade if both ZP and LB grades are present
     *
     * @return The average grade or null if either grade is missing
     */
    public Float getAverageGrade() {
        if (zpNote != null && lbNote != null) {
            return (zpNote + lbNote) / 2.0f;
        }
        return null;
    }

    /**
     * Checks if this module has all required grades
     *
     * @return true if both ZP and LB grades are present
     */
    public boolean hasAllGrades() {
        return zpNote != null && lbNote != null;
    }
}