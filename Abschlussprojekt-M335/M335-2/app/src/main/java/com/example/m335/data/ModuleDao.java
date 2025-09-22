package com.example.m335.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.m335.model.Module;

import java.util.List;

/**
 * Data Access Object for the Module entity.
 * Provides methods to interact with the module database.
 */
@Dao
public interface ModuleDao {

    /**
     * Inserts a module into the database
     *
     * @param module The module to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Module module);

    /**
     * Updates an existing module in the database
     *
     * @param module The module to update
     */
    @Update
    void update(Module module);

    /**
     * Deletes a module from the database
     *
     * @param module The module to delete
     */
    @Delete
    void delete(Module module);

    /**
     * Gets all modules from the database
     *
     * @return LiveData containing a list of all modules
     */
    @Query("SELECT * FROM modules ORDER BY moduleNumber ASC")
    LiveData<List<Module>> getAllModules();

    /**
     * Gets a module by its module number
     *
     * @param moduleNumber The module number to search for
     * @return The module with the specified module number
     */
    @Query("SELECT * FROM modules WHERE moduleNumber = :moduleNumber")
    LiveData<Module> getModuleByNumber(String moduleNumber);
}