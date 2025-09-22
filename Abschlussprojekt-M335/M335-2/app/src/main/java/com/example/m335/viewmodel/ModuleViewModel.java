package com.example.m335.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.m335.data.ModuleRepository;
import com.example.m335.model.Module;

import java.util.List;

/**
 * ViewModel to store and manage UI-related data in a lifecycle conscious way
 */
public class ModuleViewModel extends AndroidViewModel {
    private ModuleRepository repository;
    private LiveData<List<Module>> allModules;

    /**
     * Constructor initializes the repository and data
     *
     * @param application The application context
     */
    public ModuleViewModel(@NonNull Application application) {
        super(application);
        repository = new ModuleRepository(application);
        allModules = repository.getAllModules();
    }

    /**
     * Gets all modules from the repository
     *
     * @return LiveData containing a list of all modules
     */
    public LiveData<List<Module>> getAllModules() {
        return allModules;
    }

    /**
     * Gets a module by its module number
     *
     * @param moduleNumber The module number to search for
     * @return LiveData containing the module with the specified number
     */
    public LiveData<Module> getModuleByNumber(String moduleNumber) {
        return repository.getModuleByNumber(moduleNumber);
    }

    /**
     * Inserts a module into the repository
     *
     * @param module The module to insert
     */
    public void insert(Module module) {
        repository.insert(module);
    }

    /**
     * Updates a module in the repository
     *
     * @param module The module to update
     */
    public void update(Module module) {
        repository.update(module);
    }

    /**
     * Deletes a module from the repository
     *
     * @param module The module to delete
     */
    public void delete(Module module) {
        repository.delete(module);
    }

    /**
     * Validates a module before saving
     *
     * @param moduleNumber The module number to validate
     * @param moduleTitle The module title to validate
     * @param zpNote The ZP grade to validate (can be null)
     * @param lbNote The LB grade to validate (can be null)
     * @return An error message, or null if validation passed
     */
    public String validateModule(String moduleNumber, String moduleTitle,
                                 String zpNote, String lbNote) {
        // Check required fields
        if (moduleNumber == null || moduleNumber.trim().length() < 4) {
            return "Modulnummer muss mindestens 4 Zeichen lang sein";
        }

        if (moduleTitle == null || moduleTitle.trim().length() < 4) {
            return "Modultitel muss mindestens 4 Zeichen lang sein";
        }

        // Validate grade format if provided
        if (zpNote != null && !zpNote.isEmpty()) {
            try {
                float note = Float.parseFloat(zpNote);
                if (note < 1.0f || note > 6.0f) {
                    return "ZP-Note muss zwischen 1.0 und 6.0 liegen";
                }
            } catch (NumberFormatException e) {
                return "ZP-Note muss eine gültige Zahl sein";
            }
        }

        if (lbNote != null && !lbNote.isEmpty()) {
            try {
                float note = Float.parseFloat(lbNote);
                if (note < 1.0f || note > 6.0f) {
                    return "LB-Note muss zwischen 1.0 und 6.0 liegen";
                }
            } catch (NumberFormatException e) {
                return "LB-Note muss eine gültige Zahl sein";
            }
        }

        return null; // Validation passed
    }
}